//---------------------------------------------------------------------
//  
//                 rs2d.sequence.onpulseslc.OnePulse_Slc_dev
//  
// ---------------------------------------------------------------------
//GRADIENT_REF_TIME to GRADIENT_REFOC_TIME
//  V5.4   -  20 / 12 / 2017 - Adjust_Window + Hardware shim
//  V5.3   -  28 / 11 / 2017 - JR extTrigSource
//                              Version5.3 - Field-Oscilatio
//  V5.2   -  28 / 11 / 2017 - JR bug SW
//  V5.1   -  30 / 10 / 2017 - JR
//       sequenceVersion
//  V5   -  30 / 10 / 2017 - JR
//  V4_1  -  03 / 08 / 2017 -
// 		remove TX_SAVE_IN_INSTRUMENT  TX_RF_POWER
//        Add trigger
//  V4_0  -  PECIMEN to SUBJECT - 
//  V3  -  20 / 06 / 2017 - 
//	remove  "/ nucleus.getRatio()"
//
//  last modification: - 
//  18 mai 2016 add slice selection
//  19 Jul 2016 add Spoiler and nutation curve
package rs2d.sequence.onpulseslc;

import rs2d.commons.log.Log;

import java.util.*;

import rs2d.sequence.common.*;
import rs2d.spinlab.hardware.controller.HardwareHandler;
import rs2d.spinlab.instrument.Instrument;
import rs2d.spinlab.instrument.InstrumentTxChannel;
import rs2d.spinlab.instrument.util.GradientMath;
import rs2d.spinlab.sequence.SequenceTool;
import rs2d.spinlab.sequence.element.TimeElement;
import rs2d.spinlab.sequence.table.*;
import rs2d.spinlab.sequenceGenerator.SequenceGeneratorAbstract;
import rs2d.spinlab.tools.param.*;
import rs2d.spinlab.tools.role.RoleEnum;
import rs2d.spinlab.tools.table.Order;
import rs2d.spinlab.tools.utility.Nucleus;

import static java.util.Arrays.asList;

import static rs2d.sequence.onpulseslc.OnepulseSlcParams.*;
import static rs2d.sequence.onpulseslc.OnepulseSlcSequenceParams.*;


public class OnepulseSlc extends SequenceGeneratorAbstract {

    private String sequenceVersion = "Version5.6";
    public double protonFrequency;
    public double observeFrequency;
    private double min_time_per_acq_point;
    private double gMax;
    private Nucleus nucleus;

    private boolean isMultiplanar;

    private int acquisitionMatrixDimension1D;
    private int acquisitionMatrixDimension2D;
    private int acquisitionMatrixDimension3D;
    private int acquisitionMatrixDimension4D;
    private int userMatrixDimension1D;
    private int userMatrixDimension2D;
    private int userMatrixDimension3D;

    private int nb_scan_2d;
    private int nb_scan_3d;
    private int nb_scan_4d;
    private int echoTrainLength;
    int nbOfInterleavedSlice;

    private double spectralWidth;
    private boolean isSW;
    private double tr;
    private double te;

    private double sliceThickness;
    private double off_center_distance_1D;
    private double off_center_distance_2D;
    private double off_center_distance_3D;

    private double txLength;

    private boolean is_tx_amp_att_auto;
    private boolean is_tx_nutation_amp;
    private boolean is_tx_nutation_Length;
    private boolean isDelayEcho;


    private boolean isTrigger;
    private ListNumberParam triggerTime;
    private int numberOfTrigger;

    private double observation_time;

    // get hardware memory limit
    private int offset_channel_memory = 512;
    private int phase_channel_memory = 512;
    private int amp_channel_memory = 2048;
    private int loopIndice_memory = 2048;

    private double defaultInstructionDelay = 0.000010;     // single instruction minimal duration
    private double minInstructionDelay = 0.000005;     // single instruction minimal duration


    public OnepulseSlc() {


        super();
        initParam();

        init();
    }

    @Override
    public void route() throws rs2d.spinlab.hardware.compiler.exception.CompilerRoutageException {
        ((ListNumberParam) this.getParamFromName(MriDefaultParams.TX_ROUTE.name())).setValue(new ArrayList());
        super.route();
    }

    @Override
    public void init() {
        super.init();
        // Define default, min, max and suggested values regarding the instrument.
        List<String> extTrigSource = asList(
                SequenceTool.ExtTrigSource.Ext1.name(),
                SequenceTool.ExtTrigSource.Ext2.name(),
                SequenceTool.ExtTrigSource.Ext1_AND_Ext2.name(),
                SequenceTool.ExtTrigSource.Ext1_XOR_Ext2.name());
        //List<String> tx_shape = Arrays.asList("HARD", "GAUSSIAN", "SIN3", "xSINC5");
        ((TextParam) getParam(TRIGGER_CHANEL)).setSuggestedValues(extTrigSource);
        ((TextParam) getParam(TRIGGER_CHANEL)).setRestrictedToSuggested(true);

        //fitSWToHardware(12.5e3);
        getParam(DIGITAL_FILTER_REMOVED).setDefaultValue(Instrument.instance().getDevices().getCameleon().isRemoveAcquDeadPoint());
        getParam(INTERMEDIATE_FREQUENCY).setDefaultValue(Instrument.instance().getIfFrequency());
    }

    public void generate() throws Exception {
        initUserParam();
        this.beforeRouting();
        if (!this.isRouted()) {
            this.route();
            this.initAfterRouting();
        }
        this.afterRouting();
        this.checkAndFireException();
    }

    private void initUserParam() {
        isMultiplanar = (((BooleanParam) getParam(MULTI_PLANAR_EXCITATION)).getValue());

//        acquisitionMatrixDimension1D = ((NumberParam) getParam(ACQUISITION_MATRIX_DIMENSION_1D)).getValue().intValue();
        acquisitionMatrixDimension2D = ((NumberParam) getParam(ACQUISITION_MATRIX_DIMENSION_2D)).getValue().intValue();
        acquisitionMatrixDimension3D = ((NumberParam) getParam(ACQUISITION_MATRIX_DIMENSION_3D)).getValue().intValue();
        acquisitionMatrixDimension4D = ((NumberParam) getParam(ACQUISITION_MATRIX_DIMENSION_4D)).getValue().intValue();

        userMatrixDimension1D = ((NumberParam) getParam(USER_MATRIX_DIMENSION_1D)).getValue().intValue();
        userMatrixDimension2D = ((NumberParam) getParam(USER_MATRIX_DIMENSION_2D)).getValue().intValue();
        userMatrixDimension3D = ((NumberParam) getParam(USER_MATRIX_DIMENSION_3D)).getValue().intValue();

        spectralWidth = ((NumberParam) getParam(SPECTRAL_WIDTH)).getValue().doubleValue();            // get user defined spectral width
        isSW = (((BooleanParam) getParam(SPECTRAL_WIDTH_OPT)).getValue());
        tr = ((NumberParam) getParam(REPETITION_TIME)).getValue().doubleValue();
        te = ((NumberParam) getParam(ECHO_TIME)).getValue().doubleValue();

        sliceThickness = ((NumberParam) getParam(SLICE_THICKNESS)).getValue().doubleValue();

        txLength = ((NumberParam) getParam(TX_LENGTH)).getValue().doubleValue();
        is_tx_amp_att_auto = ((BooleanParam) getParam(TX_AMP_ATT_AUTO)).getValue();
        is_tx_nutation_amp = ((BooleanParam) getParam(TX_NUTATION_AMP)).getValue();
        is_tx_nutation_Length = ((BooleanParam) getParam(TX_NUTATION_LENGTH)).getValue();
        isDelayEcho = (((BooleanParam) getParam(DELAY_ECHO)).getValue());

        isTrigger = ((BooleanParam) getParam(TRIGGER_EXTERNAL)).getValue();
        triggerTime = (ListNumberParam) getParam(TRIGGER_TIME);
        numberOfTrigger = isTrigger ? triggerTime.getValue().size() : 1;
        isTrigger = isTrigger && (numberOfTrigger >= 1);


        observation_time = ((NumberParam) getParam(ACQUISITION_TIME_PER_SCAN)).getValue().doubleValue();
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------
    // -- INIT AFTER ROUTING --- INIT AFTER ROUTING --- INIT AFTER ROUTING --- INIT AFTER ROUTING --- INIT AFTER ROUTING --- INIT AFTER ROUTING --
    // --------------------------------------------------------------------------------------------------------------------------------------------
    //
    //                                                          INIT AFTER ROUTING
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------
    private void initAfterRouting() {
    }
// --------------------------------------------------------------------------------------------------------------------------------------------
// -- BEFORE ROUTING --- BEFORE ROUTING --- BEFORE ROUTING --- BEFORE ROUTING --- BEFORE ROUTING --- BEFORE ROUTING --- BEFORE ROUTING --- 
// --------------------------------------------------------------------------------------------------------------------------------------------
//
//                                                          BEFORE ROUTING
//
// --------------------------------------------------------------------------------------------------------------------------------------------

    private void beforeRouting() throws Exception {

        Log.debug(getClass(), "------------ BEFORE ROUTING -------------");

        setParamValue(SEQUENCE_VERSION, sequenceVersion);
        setParamValue(MODALITY, "MRI");
        // -----------------------------------------------
        // RX parameters : nucleus, RX gain & frequencies
        // -----------------------------------------------
        nucleus = Nucleus.getNucleusForName((String) getParam(NUCLEUS_1).getValue());
        protonFrequency = Instrument.instance().getDevices().getMagnet().getProtonFrequency();
        double freq_offset1 = ((NumberParam) getParam(OFFSET_FREQ_1)).getValue().doubleValue();
        boolean adjustWindow = (((BooleanParam) getParam(ADJUST_WINDOW)).getValue());
        double baseFreq1;
        if (adjustWindow) {
            baseFreq1 = ((NumberParam) getParam(BASE_FREQ_1)).getValue().doubleValue();
        } else {
            baseFreq1 = nucleus.getFrequency(protonFrequency);
        }
        observeFrequency = baseFreq1 + freq_offset1;
        setParamValue(BASE_FREQ_1, baseFreq1);

        min_time_per_acq_point = HardwareHandler.getInstance().getSequenceHandler().getCompiler().getTransfertTimePerDataPt();
        gMax = GradientMath.getMaxGradientStrength();

        setSequenceParamValue(Rx_gain, RECEIVER_GAIN);
        setParamValue(RECEIVER_COUNT, Instrument.instance().getObservableRxs(nucleus).size());

        setSequenceParamValue(Intermediate_frequency, Instrument.instance().getIfFrequency());
        setParamValue(INTERMEDIATE_FREQUENCY, Instrument.instance().getIfFrequency());

        setSequenceParamValue(Tx_frequency, observeFrequency);
        setParamValue(OBSERVED_FREQUENCY, observeFrequency);

        setSequenceParamValue(Tx_nucleus, NUCLEUS_1);
        setParamValue(OBSERVED_NUCLEUS, nucleus);

        // -----------------------------------------------
        // 1stD managment     
        // -----------------------------------------------   
        // MATRIX
        acquisitionMatrixDimension1D = userMatrixDimension1D;

        double spectralWidthPerPixel = ((NumberParam) getParam(SPECTRAL_WIDTH_PER_PIXEL)).getValue().doubleValue();
        spectralWidth = isSW ? spectralWidth : spectralWidthPerPixel * acquisitionMatrixDimension1D;

        spectralWidth = HardwareHandler.getInstance().getSequenceHandler().getCompiler().getNearestSW(spectralWidth);      // get real spectral width from Chameleon
        double spectralWidthUP = spectralWidth;
        spectralWidthPerPixel = spectralWidth / acquisitionMatrixDimension1D;
        setParamValue(SPECTRAL_WIDTH_PER_PIXEL, spectralWidthPerPixel);
        setParamValue(SPECTRAL_WIDTH, spectralWidthUP);
        observation_time = acquisitionMatrixDimension1D / spectralWidth;
        setParamValue(ACQUISITION_TIME_PER_SCAN, observation_time);   // display observation time

        // -----------------------------------------------
        // 2nd D managment 
        // -----------------------------------------------
        // MATRIX

        if (!is_tx_amp_att_auto && (is_tx_nutation_amp || is_tx_nutation_Length)) {
            int tx_number = ((NumberParam) getParam(is_tx_nutation_amp ? TX_AMP_NUMBER : TX_LENGTH_NUMBER)).getValue().intValue();
            double tx_amp_start = ((NumberParam) getParam(TX_AMP_START)).getValue().doubleValue();
            double tx_amp_step = ((NumberParam) getParam(TX_AMP_STEP)).getValue().doubleValue();

            acquisitionMatrixDimension2D = Math.min(tx_number, is_tx_nutation_amp ? (int) Math.floor((100 - tx_amp_start) / (tx_amp_step - 1)) : tx_number);


            nb_scan_2d = acquisitionMatrixDimension2D;
            setParamValue(USER_MATRIX_DIMENSION_2D, acquisitionMatrixDimension2D);

            is_tx_nutation_Length = !is_tx_nutation_amp && is_tx_nutation_Length;
            setParamValue(TX_NUTATION_LENGTH, is_tx_nutation_Length);
            isMultiplanar = !is_tx_nutation_Length && isMultiplanar;
            setParamValue(MULTI_PLANAR_EXCITATION, isMultiplanar);

            isDelayEcho = !is_tx_nutation_Length && isDelayEcho;
            setParamValue(DELAY_ECHO, isDelayEcho);

        } else {
            acquisitionMatrixDimension2D = userMatrixDimension2D;
            nb_scan_2d = userMatrixDimension2D;

        }
        // -----------------------------------------------
        // 3D managment 
        // ------------------------------------------------
        // MATRIX

        nb_scan_3d = userMatrixDimension3D;

        // -----------------------------------------------
        // 4D managment:  Dynamic, MultiEcho, External triggering
        // -----------------------------------------------   

        nb_scan_4d = numberOfTrigger;
        acquisitionMatrixDimension4D = nb_scan_4d;
        setParamValue(USER_MATRIX_DIMENSION_4D, nb_scan_4d);

        // -----------------------------------------------
        // set the ACQUISITION_MATRIX and Nb XD
        // -----------------------------------------------        // set the calculated acquisition matrix
        setParamValue(ACQUISITION_MATRIX_DIMENSION_1D, acquisitionMatrixDimension1D);
        setParamValue(ACQUISITION_MATRIX_DIMENSION_2D, acquisitionMatrixDimension2D);
        setParamValue(ACQUISITION_MATRIX_DIMENSION_3D, acquisitionMatrixDimension3D);
        setParamValue(ACQUISITION_MATRIX_DIMENSION_4D, acquisitionMatrixDimension4D);

        // set the calculated sequence dimensions 
        setSequenceParamValue(Pre_scan, DUMMY_SCAN); // Do the prescan 
        setSequenceParamValue(Nb_point, acquisitionMatrixDimension1D);
        setSequenceParamValue(Nb_1d, NUMBER_OF_AVERAGES);
        setSequenceParamValue(Nb_2d, nb_scan_2d);
        setSequenceParamValue(Nb_3d, nb_scan_3d);
        setSequenceParamValue(Nb_4d, nb_scan_4d);
        // -----------------------------------------------
        // Image Orientation
        // -----------------------------------------------
        //READ PHASE and SLICE matrix
        //Offset according to animal position
        off_center_distance_3D = ((NumberParam) getParam(OFF_CENTER_FIELD_OF_VIEW_3D)).getValue().doubleValue();
        off_center_distance_1D = getOff_center_distance_1D_2D_3D(1);
        off_center_distance_2D = getOff_center_distance_1D_2D_3D(2);
        off_center_distance_3D = getOff_center_distance_1D_2D_3D(3);

        if (!isMultiplanar) {
            off_center_distance_3D = 0;
        }
        off_center_distance_1D = 0;
        off_center_distance_2D = 0;
        setParamValue(OFF_CENTER_FIELD_OF_VIEW_3D, off_center_distance_3D);
        setParamValue(OFF_CENTER_FIELD_OF_VIEW_2D, off_center_distance_2D);
        setParamValue(OFF_CENTER_FIELD_OF_VIEW_1D, off_center_distance_1D);

        ArrayList<Number> off_center_distanceList = new ArrayList<>();
        off_center_distanceList.add(0);
        off_center_distanceList.add(0);
        off_center_distanceList.add(off_center_distance_3D);

        setParamValue(OFF_CENTER_FIELD_OF_VIEW_EFF, off_center_distanceList);


        HardwarePreemphasis hardwarePreemphasis = new HardwarePreemphasis();
        setParamValue(HARDWARE_PREEMPHASIS_A, hardwarePreemphasis.getAmplitude());
        setParamValue(HARDWARE_PREEMPHASIS_T, hardwarePreemphasis.getTime());
        setParamValue(HARDWARE_DC, hardwarePreemphasis.getDC());
        setParamValue(HARDWARE_A0, hardwarePreemphasis.getA0());

        HardwareShim hardwareShim = new HardwareShim();
        setParamValue(HARDWARE_SHIM, hardwareShim.getValue());
        setParamValue(HARDWARE_SHIM_LABEL, hardwareShim.getLabel());
        // ------------------------------------------------------------------
        // load preemphasis
        // ------------------------------------------------------------------
        HardwareB0comp hardwareB0comp = new HardwareB0comp();
        setParamValue(HARDWARE_B0_COMP, hardwareB0comp.getStatus());
        ;
        setParamValue(HARDWARE_B0_COMP_AMP, hardwareB0comp.getAmp());
        ;
        setParamValue(HARDWARE_B0_COMP_PHASE, hardwareB0comp.getB0compPhase());
        // -----------------------------------------------
        // activate gradient rotation matrix
        // -----------------------------------------------
        appliedGradientRotation();
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------
    // -- AFTER ROUTING --- AFTER ROUTING --- AFTER ROUTING --- AFTER ROUTING --- AFTER ROUTING --- AFTER ROUTING --- AFTER ROUTING ---  AFTER ROUTING --- 
    // --------------------------------------------------------------------------------------------------------------------------------------------
    //
    //                                                          AFTER ROUTING
    //
    // --------------------------------------------------------------------------------------------------------------------------------------------

    private void afterRouting() throws Exception {
        Log.debug(getClass(), "------------ AFTER ROUTING -------------");

        // -----------------------------------------------
        // enable gradient lines
        // -----------------------------------------------
        setSequenceParamValue(enabled_slice, isMultiplanar);
        setSequenceParamValue(enabled_spoiler, GRADIENT_ENABLE_SPOILER);
        // -----------------------------------------------
        // calculate gradient equivalent rise time
        // -----------------------------------------------
        double grad_rise_time = ((NumberParam) getParam(GRADIENT_RISE_TIME)).getValue().doubleValue();
        double min_rise_time_factor = ((NumberParam) getParam(MIN_RISE_TIME_FACTOR)).getValue().doubleValue();

        double min_rise_time_sinus = GradientMath.getShortestRiseTime(100.0) * Math.PI / 2 * 100 / min_rise_time_factor;
        double min_time = GradientMath.getShortestRiseTime(100.0);

        if (grad_rise_time < min_rise_time_sinus) {
            double new_grad_rise_time = ceilToSubDecimal(min_rise_time_sinus, 5);
            getUnreachParamExceptionManager().addParam(GRADIENT_RISE_TIME.name(), grad_rise_time, new_grad_rise_time, ((NumberParam) getParam(GRADIENT_RISE_TIME)).getMaxValue(), "Gradient ramp time too short ");
            grad_rise_time = new_grad_rise_time;
        }
        setSequenceTableSingleValue(Time_grad_ramp, grad_rise_time);


        double grad_shape_rise_factor_up = Utility.voltageFillingFactor((Shape) getSequence().getPublicTable(Grad_shape_up));
        double grad_shape_rise_factor_down = Utility.voltageFillingFactor((Shape) getSequence().getPublicTable(Grad_shape_down));
        double grad_shape_rise_time = grad_shape_rise_factor_up * grad_rise_time + grad_shape_rise_factor_down * grad_rise_time;        // shape dependant equivalent rise time

        // -----------------------------------------------
        // Calculation RF pulse parameters  1/3 : Shape
        // -----------------------------------------------
        Table txLengthTable = setSequenceTableValues(Time_tx, Order.Two);
        txLengthTable.add(txLength);

        RFPulse pulseTX = RFPulse.createRFPulse(getSequence(), Tx_att, Tx_amp1, Tx_phase, Time_tx, Tx_shape, Tx_shape_phase, Tx_freq_offset);

        int nb_shape_points = 128;
        pulseTX.setShape(((String) getParam(TX_SHAPE).getValue()), nb_shape_points, "Hamming");

        // -----------------------------------------------
        // Calculation RF pulse parameters  2/3 : RF pulse & attenuation
        // -----------------------------------------------
        double flip_angle = ((NumberParam) getParam(FLIP_ANGLE)).getValue().doubleValue();

        double pulsePowerWatt_pulse;
        double tx_amp;
        int tx_att;
        if (is_tx_amp_att_auto) {
            if (!pulseTX.setAutoCalibFor180(flip_angle, observeFrequency, ((List<Integer>) getParam(TX_ROUTE).getValue()), nucleus)) {
                getUnreachParamExceptionManager().addParam(TX_LENGTH.name(), txLength, pulseTX.getPulseDuration(), ((NumberParam) getParam(TX_LENGTH)).getMaxValue(), "Pulse length too short to reach RF power with this pulse shape");
                txLength = pulseTX.getPulseDuration();
            }
            tx_amp = pulseTX.getAmp();
            tx_att = pulseTX.getAtt();
        } else {
            tx_amp = ((NumberParam) getParam(TX_AMP)).getValue().doubleValue();
            tx_att = ((NumberParam) getParam(TX_ATT)).getValue().intValue();

        }
        if (is_tx_nutation_amp) {
            double tx_amp_start = ((NumberParam) getParam(TX_AMP_START)).getValue().doubleValue();
            double tx_amp_step = ((NumberParam) getParam(TX_AMP_STEP)).getValue().doubleValue();

            double[] tx_amps = new double[acquisitionMatrixDimension2D];
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                tx_amps[i] = (tx_amp_start + i * tx_amp_step);
            }
            pulseTX.setAmp(Order.Two, tx_amps);
            pulseTX.setAtt(tx_att);
            this.setParamValue(TX_ATT, tx_att);
            //tx_amp = tx_amps[0];
            //      pulsePowerWatt_pulse = (float) TxMath.getPowerWatt(tx_amp, tx_att, observe_frequency, txCh) * power_factor_90;
        } else {
            pulseTX.setAmp(tx_amp);
            pulseTX.setAtt(tx_att);
            //    pulsePowerWatt_pulse = pulseTX.get (float) TxMath.getPowerWatt(tx_amp, tx_att, observe_frequency, txCh) * power_factor_90;
            this.setParamValue(TX_AMP, tx_amp);
            this.setParamValue(TX_ATT, tx_att);
        }
        double txLengthMax = txLength;
        double[] tx_lengths = new double[acquisitionMatrixDimension2D];
        if (is_tx_nutation_Length) {
            Order order = txLengthTable.getOrder();
            txLengthTable.clear();
            txLengthTable.setOrder(order);
            double tx_start = ((NumberParam) getParam(TX_LENGTH_START)).getValue().doubleValue();
            double tx_step = ((NumberParam) getParam(TX_LENGTH_STEP)).getValue().doubleValue();

            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                tx_lengths[i] = (tx_start + i * tx_step);
                txLengthTable.add(tx_lengths[i]);
            }
            txLengthMax = tx_lengths[acquisitionMatrixDimension2D - 1];
        }


        // -----------------------------------------------
        // Calculation RF pulse parameters  3/3: bandwidth
        // -----------------------------------------------
        double tx_bandwidth_factor;
        if ("GAUSSIAN".equalsIgnoreCase((String) getParam(TX_SHAPE).getValue())) {
            tx_bandwidth_factor = 1.35;
        } else if ("SINC3".equalsIgnoreCase((String) getParam(TX_SHAPE).getValue())) {
            tx_bandwidth_factor = 2.55;
        } else if ("SINC5".equalsIgnoreCase((String) getParam(TX_SHAPE).getValue())) {
            tx_bandwidth_factor = 4.25;
        } else {
            tx_bandwidth_factor = 0.95;
        }
        double tx_bandwidth = tx_bandwidth_factor / txLength;

        // ---------------------------------------------------------------------
        // calculate SLICE gradient amplitudes for RF pulses
        // ---------------------------------------------------------------------
        double slice_thickness_excitation = (sliceThickness);
        setSequenceTableFirstValue(Time_grad_ramp, isMultiplanar ? grad_rise_time : minInstructionDelay);
        InstrumentTxChannel txCh = Instrument.instance().getTxChannels().get(((List<Integer>) getParam(TX_ROUTE).getValue()).get(0));
        double blanking_time = txCh.getRfAmpChannel().getBlankingDelay();
        setSequenceTableSingleValue(Time_grad_ramp_blanking, Math.max(isMultiplanar ? grad_rise_time : minInstructionDelay, blanking_time));
// 15 40 2.5m

        Gradient gradSlice = Gradient.createGradient(getSequence(), Grad_amp_slice, Time_tx, Grad_shape_up, Grad_shape_down, Time_grad_ramp);
        if (isMultiplanar && !gradSlice.prepareSliceSelection(tx_bandwidth, slice_thickness_excitation))

        {
            slice_thickness_excitation = gradSlice.getSliceThickness();
            double slice_thickness_min = (isMultiplanar ? slice_thickness_excitation : (slice_thickness_excitation / userMatrixDimension3D));
            getUnreachParamExceptionManager().addParam(SLICE_THICKNESS.name(), sliceThickness, slice_thickness_min, ((NumberParam) getParam(SLICE_THICKNESS)).getMaxValue(), "Pulse length too short to reach this slice thickness");
            sliceThickness = slice_thickness_min;
        }
        gradSlice.applyAmplitude();

        // calculate SLICE_refocusing
        double grad_ref_application_time = ((NumberParam) getParam(GRADIENT_REFOC_TIME)).getValue().doubleValue();
        setSequenceTableFirstValue(Time_grad_ref, isMultiplanar ? grad_ref_application_time : minInstructionDelay);
        Gradient gradSliceRefPhase3D = Gradient.createGradient(getSequence(), Grad_amp_slice_ref, Time_grad_ref, Grad_shape_up, Grad_shape_down, Time_grad_ramp);
        if (isMultiplanar) {
            gradSliceRefPhase3D.refocalizeGradient(gradSlice, 0.5);
        }

        // -----------------------------------------------
        // calculate ADC observation time
        // -----------------------------------------------

        setSequenceTableFirstValue(Time_rx, observation_time);
        setSequenceParamValue(Spectral_width, spectralWidth);
        // ---------------------------------------------------------------------
        // calculate SPOILER gradient amplitudes
        // ---------------------------------------------------------------------
        boolean is_spoiler = (((BooleanParam) getParam(GRADIENT_ENABLE_SPOILER)).getValue());
        double grad_spoiler_application_time = ((NumberParam) getParam(GRADIENT_SPOILER_TIME)).getValue().doubleValue();

        setSequenceTableFirstValue(Time_grad_spoil, is_spoiler ? grad_spoiler_application_time : minInstructionDelay);

        setSequenceTableFirstValue(Time_grad_spoil_ramp, is_spoiler ? grad_rise_time : minInstructionDelay);

        double grad_spoiler_amp = ((NumberParam) getParam(GRADIENT_AMP_SPOILER)).getValue().doubleValue();

        Gradient gradSpoiler = Gradient.createGradient(getSequence(), Grad_amp_spoiler, Time_grad_spoil, Grad_shape_up, Grad_shape_down, Time_grad_spoil_ramp);
        if (((BooleanParam) getParam(GRADIENT_ENABLE_SPOILER)).getValue())
            gradSpoiler.addSpoiler(grad_spoiler_amp);
        gradSpoiler.applyAmplitude();

        // --------------------------------------------------------------------------------------------------------------------------------------------
        // TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING
        // --------------------------------------------------------------------------------------------------------------------------------------------
        //
        //                                                          index of sequence events
        //
        // --------------------------------------------------------------------------------------------------------------------------------------------
        int eventStart = 0;
        int eventPulse = 5;
        int eventDelay = 10;
        int eventAcq = 11;
        int eventFIRDelay = 12;
        int eventEnd = 16;

        // ------------------------------------------
        // calculate delays adapted to current TE & search for incoherence
        // ------------------------------------------
        // calculate actual delays between Rf-pulses and ADC
        double time0 = getTimeBetweenEvents(eventPulse + 1, eventDelay - 1) + getTimeBetweenEvents(eventDelay + 1, eventAcq - 1);
        double time1 = time0 + txLengthMax / 2;// Actual_TE

        // get minimal TE value & search for incoherence
        double max_time = ceilToSubDecimal(time1, 5);
        double te_min = max_time + minInstructionDelay;

        Table time_te_delay_table = setSequenceTableValues(Time_te_delay, Order.Two);

        boolean isDelayEcho = (((BooleanParam) getParam(DELAY_ECHO)).getValue());
        double delay1 = Math.max(minInstructionDelay, ((NumberParam) getParam(DELAY_ECHO_TIME)).getValue().doubleValue());
        if (isDelayEcho) {
            te = delay1 + time1;
            setParamValue(ECHO_TIME, te);

            delay1 = te - time1;
            time_te_delay_table.add(delay1);
        } else {
            if (te < te_min) {
                te_min = ceilToSubDecimal(te_min, 5);
                getUnreachParamExceptionManager().addParam(ECHO_TIME.name(), te, te_min, ((NumberParam) getParam(ECHO_TIME)).getMaxValue(), "TE too short for the User Mx1D and SW");
                te = te_min;//
            }
            if (is_tx_nutation_Length) {
                for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                    time_te_delay_table.add(te - time0 -tx_lengths[i] / 2);
                }
            } else {
                delay1 = te - time1;
                time_te_delay_table.add(delay1);
                setParamValue(DELAY_ECHO_TIME, delay1);
            }
        }
        // set calculated the pulseDuration delays to get the proper TE


        // ------------------------------------------
        // delays for FIR
        // ------------------------------------------

        boolean is_FIR = Instrument.instance().getDevices().getCameleon().isRemoveAcquDeadPoint();
        double lo_FIR_dead_point = is_FIR ? Instrument.instance().getDevices().getCameleon().getAcquDeadPointCount() : 0;
        double min_FIR_delay = (lo_FIR_dead_point + 2) / spectralWidth;
        double min_FIR_4pts_delay = 4 / spectralWidth;

        double time_fir = getTimeBetweenEvents(eventAcq + 1, eventEnd - 1);
        time_fir = removeTimeForEvents(time_fir, eventFIRDelay); // Actual_TE without delay1
        System.out.println("time_fir " + time_fir);
        double time_fir_delay = Math.max(minInstructionDelay, min_FIR_4pts_delay - time_fir);
        time_fir = getTimeBetweenEvents(eventAcq + 1, eventEnd - 1);

        setSequenceTableFirstValue(Time_FIR_delay, time_fir_delay);

        //--------------------------------------------------------------------------------------
        //  External triggering
        //--------------------------------------------------------------------------------------
        getSequence().getPublicParam(Synchro_trigger).setValue(isTrigger ? TimeElement.Trigger.External : TimeElement.Trigger.Timer);
        getSequence().getPublicParam(Synchro_trigger).setLocked(true);

        double time_external_trigger_delay_max = minInstructionDelay;

        Table triggerdelay = getSequence().getTable(Time_trigger_delay);
        triggerdelay.clear();

        if ((!isTrigger)) {
            triggerdelay.add(minInstructionDelay);
        } else {
            for (int i = 0; i < numberOfTrigger; i++) {
                double time_external_trigger_delay = Math.round(triggerTime.getValue().get(i).doubleValue() * Math.pow(10, 7)) / Math.pow(10, 7);
                time_external_trigger_delay = time_external_trigger_delay < minInstructionDelay ? minInstructionDelay : time_external_trigger_delay;
                triggerdelay.add(time_external_trigger_delay);
                time_external_trigger_delay_max = Math.max(time_external_trigger_delay_max, time_external_trigger_delay);
            }
            triggerdelay.setOrder(Order.Four);
        }

        setSequenceParamValue(Ext_trig_source, TRIGGER_CHANEL);
        // ---------------------------------------------------------------
        // calculate TR , Time_last_delay  Time_TR_delay & search for incoherence
        // ---------------------------------------------------------------
        double min_flush_delay = minInstructionDelay;   // minimal time to flush Chameleon buffer (this time is doubled to avoid hidden delays);
        min_flush_delay = Math.max(min_FIR_delay - time_fir, minInstructionDelay);

        double time_seq_to_end_spoiler0 = getTimeBetweenEvents(eventStart, eventPulse - 1) +te + getTimeBetweenEvents(eventAcq, eventEnd - 1);
        double time_seq_to_end_spoiler = time_seq_to_end_spoiler0 + txLengthMax / 2 ;
        double tr_min = time_seq_to_end_spoiler + min_flush_delay;// 2 +( 2 minInstructionDelay: event 22 +(20&21
        if (tr < tr_min) {
            System.out.println(tr + " < " + tr_min);
            tr_min = Math.ceil(tr_min * Math.pow(10, 4)) / Math.pow(10, 4);
            this.getUnreachParamExceptionManager().addParam(REPETITION_TIME.name(), tr, tr_min, ((NumberParam) getParam(REPETITION_TIME)).getMaxValue(), "TR too short to reach");
            tr = tr_min;
        }
        System.out.println(" tr =  " + tr);
        // ------------------------------------------
        // set calculated TR
        // ------------------------------------------
        // set  TR delay to compensate and trigger delays
        Table time_last_delay_table = setSequenceTableValues(Time_last_delay, Order.Two);
        if (is_tx_nutation_Length) {
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                time_last_delay_table.add(tr - time_seq_to_end_spoiler0 - tx_lengths[i] / 2 );
            }
        } else {
            double last_delay = tr - time_seq_to_end_spoiler;
            time_last_delay_table.add(last_delay);
        }


        double total_acquisition_time = tr * nb_scan_4d * nb_scan_3d * nb_scan_2d;
        setParamValue(SEQUENCE_TIME, total_acquisition_time);

        // ------------------------------------------------------------------
        // calculate TX FREQUENCY offsets tables for multi-slice acquisitions and
        // ------------------------------------------------------------------
        double grad_amp_slice_mTpm = (tx_bandwidth / ((GradientMath.GAMMA) * slice_thickness_excitation));
        double frequency_center_3D_90 = -grad_amp_slice_mTpm * off_center_distance_3D * (GradientMath.GAMMA);
        if (!isMultiplanar) {
            frequency_center_3D_90 = 0;
        }
        setSequenceTableFirstValue(Tx_freq_offset, frequency_center_3D_90);

    }


    private double ceilToSubDecimal(double numberToBeRounded, double Order) {
        return Math.ceil(numberToBeRounded * Math.pow(10, Order)) / Math.pow(10, Order);
    }

    private double roundToDecimal(double numberToBeRounded, double order) {
        return Math.round(numberToBeRounded * Math.pow(10, order)) / Math.pow(10, order);
    }


    private Table setSequenceTableSingleValue(String tableName, double... values) {
        // uses Order.One because there are no tables in this dimension: compilation issue
        return setSequenceTableValues(tableName, Order.FourLoop, values);
    }

    private Table setSequenceTableValues(String tableName, Order order, double... values) {
        Table table = getSequence().getPublicTable(tableName);
        table.clear();
        table.setOrder(order);
        table.setLocked(true);

        for (double value : values) {
            table.add(value);
        }
        return table;
    }

    private double getOff_center_distance_1D_2D_3D(int dim) {
        ListNumberParam image_orientation = (ListNumberParam) getParam(IMAGE_ORIENTATION_SUBJECT);
        double[] direction_index = new double[9];
        direction_index[0] = image_orientation.getValue().get(0).doubleValue();
        direction_index[1] = image_orientation.getValue().get(1).doubleValue();
        direction_index[2] = image_orientation.getValue().get(2).doubleValue();
        direction_index[3] = image_orientation.getValue().get(3).doubleValue();
        direction_index[4] = image_orientation.getValue().get(4).doubleValue();
        direction_index[5] = image_orientation.getValue().get(5).doubleValue();
        direction_index[6] = direction_index[1] * direction_index[5] - direction_index[2] * direction_index[4];
        direction_index[7] = direction_index[2] * direction_index[3] - direction_index[0] * direction_index[5];
        direction_index[8] = direction_index[0] * direction_index[4] - direction_index[1] * direction_index[3];

        double norm_vector_read = Math.sqrt(Math.pow(direction_index[0], 2) + Math.pow(direction_index[1], 2) + Math.pow(direction_index[2], 2));
        double norm_vector_phase = Math.sqrt(Math.pow(direction_index[3], 2) + Math.pow(direction_index[4], 2) + Math.pow(direction_index[5], 2));
        double norm_vector_slice = Math.sqrt(Math.pow(direction_index[6], 2) + Math.pow(direction_index[7], 2) + Math.pow(direction_index[8], 2));

        //Offset according to animal position
        double off_center_distance_Z = ((NumberParam) getParam(OFF_CENTER_FIELD_OF_VIEW_Z)).getValue().doubleValue();
        double off_center_distance_Y = ((NumberParam) getParam(OFF_CENTER_FIELD_OF_VIEW_Y)).getValue().doubleValue();
        double off_center_distance_X = ((NumberParam) getParam(OFF_CENTER_FIELD_OF_VIEW_X)).getValue().doubleValue();

        //Offset according to READ PHASE and SLICE
        double off_center_distance;
        switch (dim) {
            case 1:
                off_center_distance = off_center_distance_X * direction_index[0] / norm_vector_read + off_center_distance_Y * direction_index[1] / norm_vector_read + off_center_distance_Z * direction_index[2] / norm_vector_read;
                break;
            case 2:
                off_center_distance = off_center_distance_X * direction_index[3] / norm_vector_phase + off_center_distance_Y * direction_index[4] / norm_vector_phase + off_center_distance_Z * direction_index[5] / norm_vector_phase;
                break;
            case 3:
                off_center_distance = off_center_distance_X * direction_index[6] / norm_vector_slice + off_center_distance_Y * direction_index[7] / norm_vector_slice + off_center_distance_Z * direction_index[8] / norm_vector_slice;
                break;
            default:
                off_center_distance = 0;
                break;
        }
        return off_center_distance;
    }

    /**
     * Calculate the time during 2 including events correspnding to the index
     *
     * @param indexFirstEvent The index of the first time event
     * @param indexLastEvent  The index of the last time event
     * @return The total time between the 2 events (including)
     */
    public double getTimeBetweenEvents(int indexFirstEvent, int indexLastEvent) {
        double time = 0;
        for (int i = indexFirstEvent; i < indexLastEvent + 1; i++) {
            time += ((TimeElement) getSequence().getTimeChannel().get(i)).getTime().getFirst().doubleValue();
        }
        return time;
    }

    public ArrayList<RoleEnum> getPluginAccess() {
        ArrayList<RoleEnum> roleEnums = new ArrayList<RoleEnum>();
        roleEnums.add(RoleEnum.User);
        return roleEnums;
    }

    private Number getNumber(String name) {
        return (Number) getParamFromName(name).getValue();
    }


    //<editor-fold defaultstate="collapsed" desc="Generated Code (RS2D)">
    public void initParam() {
        for (OnepulseSlcParams param : OnepulseSlcParams.values()) {
            addParam(param.build());
        }
    }

    public Param getParam(OnepulseSlcParams param) {
        return getParamFromName(param.name());
    }

    public void setParamValue(OnepulseSlcParams param, Object value) {
        setParamValue(param.name(), value);
    }

    public String getName() {
        return "OnePulse_Slc";
    }

    public float getVersion() {
        return 0.0f;
    }
    //</editor-fold>


}