//---------------------------------------------------------------------
//  
//                 rs2d.sequence.onpulseslc.OnePulse_Slc_dev
//  
// ---------------------------------------------------------------------

package rs2d.sequence.onpulsegradslc;

import rs2d.commons.log.Log;

import rs2d.sequence.common.*;
import rs2d.spinlab.api.Hardware;
import rs2d.spinlab.api.PowerComputation;
import rs2d.spinlab.instrument.util.GradientMath;
import rs2d.spinlab.sequence.SequenceTool;
import rs2d.spinlab.sequence.element.TimeElement;
import rs2d.spinlab.sequence.table.*;
import rs2d.spinlab.sequenceGenerator.BaseSequenceGenerator;
import rs2d.spinlab.sequenceGenerator.util.GradientRotation;
import rs2d.spinlab.sequenceGenerator.util.TimeEvents;
import rs2d.spinlab.tools.param.*;
import rs2d.spinlab.tools.table.Order;
import rs2d.spinlab.tools.utility.Nucleus;

import java.util.*;
import static java.util.Arrays.asList;

import static rs2d.sequence.onpulsegradslc.S.*;
import static rs2d.sequence.onpulsegradslc.U.*;


public class OnepulseGradSlc extends BaseSequenceGenerator {

    private final String sequenceVersion = "Version8.7";
    public double protonFrequency;
    public double observeFrequency;
    private Nucleus nucleus;

    private boolean isMultiplanar;

    private int acquisitionMatrixDimension1D;
    private int acquisitionMatrixDimension2D;
    private int acquisitionMatrixDimension3D;
    private int acquisitionMatrixDimension4D;
    private int userMatrixDimension1D;
    private int userMatrixDimension2D;
    private int userMatrixDimension3D;

    private int nb_scan_1d;
    private int nb_scan_2d;
    private int nb_scan_3d;
    private int nb_scan_4d;

    private double spectralWidth;
    private boolean isSW;
    private double tr;
    private double te;

    private double sliceThickness;
    private double off_center_distance_1D;
    private double off_center_distance_2D;
    private double off_center_distance_3D;

    private double txLength;
    private double txVolt;

    private enum PowerInput {AmpAtt, Volt, FA}

    ;
    PowerInput powerInput;

    private enum NutationType {None, Amplitude, Voltage, Length}

    NutationType nutationType;
    private double txAmpMin;
    private double txAmpMax;
    private double txVoltMin;
    private double txVoltMax;
    private double txLengthMin;
    private double txLengthMax;
    private boolean isDelayEcho;


    private boolean isTrigger;
    private List<Double> triggerTime;
    private int numberOfTrigger;

    private double observation_time;

    // get hardware memory limit
    private final double minInstructionDelay = 0.000005;     // single instruction minimal duration
    private final double txAmpMinResolution = 0.01;
    private final double txVoltMinResolution = 0.01;
    private final double txLengthMinResolution = 128 * Math.pow(10, -9);


    public OnepulseGradSlc() {
        addUserParams();
    }

    // @Override
    public void init() {
        super.init();
        // Define default, min, max and suggested values regarding the instrument.
        List<String> extTrigSource = asList(
                SequenceTool.ExtTrigSource.Ext1.name(),
                SequenceTool.ExtTrigSource.Ext2.name(),
                SequenceTool.ExtTrigSource.Ext1_AND_Ext2.name(),
                SequenceTool.ExtTrigSource.Ext1_XOR_Ext2.name());

        ((TextParam) getParam(TRIGGER_CHANEL)).setSuggestedValues(extTrigSource);
        ((TextParam) getParam(TRIGGER_CHANEL)).setRestrictedToSuggested(true);

        List<String> tx_pow_input = asList("Att/Amp", "Voltage", "Flip Angle");
        ((TextParam) getParam(TX_POWER_INPUT)).setSuggestedValues(tx_pow_input);
        ((TextParam) getParam(TX_POWER_INPUT)).setRestrictedToSuggested(true);

        List<String> tx_nutation = asList("None", "Amplitude", "Voltage", "Length");
        ((TextParam) getParam(TX_NUTATION)).setSuggestedValues(tx_nutation);
        ((TextParam) getParam(TX_NUTATION)).setRestrictedToSuggested(true);
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
        isMultiplanar = (getBoolean(MULTI_PLANAR_EXCITATION));

//        acquisitionMatrixDimension1D = getInt(ACQUISITION_MATRIX_DIMENSION_1D);
        acquisitionMatrixDimension2D = getInt(ACQUISITION_MATRIX_DIMENSION_2D);
        acquisitionMatrixDimension3D = getInt(ACQUISITION_MATRIX_DIMENSION_3D);
        acquisitionMatrixDimension4D = getInt(ACQUISITION_MATRIX_DIMENSION_4D);

        userMatrixDimension1D = getInt(USER_MATRIX_DIMENSION_1D);
        userMatrixDimension2D = getInt(USER_MATRIX_DIMENSION_2D);
        userMatrixDimension3D = getInt(USER_MATRIX_DIMENSION_3D);
        nb_scan_1d = getInt(NUMBER_OF_AVERAGES);


        spectralWidth = getDouble(SPECTRAL_WIDTH);            // get user defined spectral width
        isSW = (getBoolean(SPECTRAL_WIDTH_OPT));
        tr = getDouble(REPETITION_TIME);
        te = getDouble(ECHO_TIME);

        sliceThickness = getDouble(SLICE_THICKNESS);

        txVolt = getDouble(TX_VOLTAGE);
        powerInput = PowerInput.valueOf(getText(TX_POWER_INPUT).equals("Att/Amp") ? "AmpAtt" : getText(TX_POWER_INPUT).equals("Voltage") ? "Volt" : "FA");
        isDelayEcho = getBoolean(DELAY_ECHO);
        // Nutation curve parameters
        nutationType = NutationType.valueOf(getText(TX_NUTATION));
        txAmpMin = getDouble(TX_NUTATION_AMP_MIN);
        txAmpMax = getDouble(TX_NUTATION_AMP_MAX);
        txVoltMin = getDouble(TX_NUTATION_VOLT_MIN);
        txVoltMax = getDouble(TX_NUTATION_VOLT_MAX);
        txLength = nutationType == NutationType.Length ? 0.001 : getDouble(TX_LENGTH); //reference for calculation of FA ref and check power
        txLengthMin = getDouble(TX_NUTATION_LENGTH_MIN);
        txLengthMax = getDouble(TX_NUTATION_LENGTH_MAX);


        isTrigger = getBoolean(TRIGGER_EXTERNAL);
        triggerTime = getListDouble(TRIGGER_TIME);
        numberOfTrigger = isTrigger ? triggerTime.size() : 1;
        isTrigger = isTrigger && (numberOfTrigger >= 1);


        observation_time = getDouble(ACQUISITION_TIME_PER_SCAN);
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

        getParam(SEQUENCE_VERSION).setValue(sequenceVersion);
        getParam(MODALITY).setValue("MRI");
        // -----------------------------------------------
        // RX parameters : nucleus, RX gain & frequencies
        // -----------------------------------------------
        nucleus = Nucleus.getNucleusForName(getText(NUCLEUS_1));
        protonFrequency = Hardware.getProtonFrequency();
        double freq_offset1 = getDouble(OFFSET_FREQ_1);
        boolean adjustWindow = (getBoolean(ADJUST_WINDOW));
        double baseFreq1;
        if (adjustWindow) {
            baseFreq1 = getDouble(BASE_FREQ_1);
        } else {
            baseFreq1 = nucleus.getFrequency(protonFrequency);
        }
        observeFrequency = baseFreq1 + freq_offset1;
        getParam(BASE_FREQ_1).setValue(baseFreq1);

        int rg = Math.min(getInt(RECEIVER_GAIN), Hardware.getMaxReceiverGain(nucleus) );
        getParam(RECEIVER_GAIN).setValue(rg);
        set(Rx_gain, rg);
        getParam(RECEIVER_COUNT).setValue(Hardware.getReceiverCount(nucleus));

        set(Intermediate_frequency, Hardware.getIntermediateFrequency(nucleus.getName(), observeFrequency));
        getParam(INTERMEDIATE_FREQUENCY).setValue(Hardware.getIntermediateFrequency(nucleus.getName(), observeFrequency));

        getParam(DIGITAL_FILTER_REMOVED).setValue(Hardware.isRemoveAcquisitionDeadPoints());
        getParam(DIGITAL_FILTER_SHIFT).setValue(Hardware.getNbAcquisitionDeadPoints());

        set(Tx_frequency, observeFrequency);
        getParam(OBSERVED_FREQUENCY).setValue(observeFrequency);

        set(Tx_nucleus, NUCLEUS_1);
        getParam(OBSERVED_NUCLEUS).setValue(nucleus);

        // -----------------------------------------------
        // 1stD managment     
        // -----------------------------------------------   
        // MATRIX
        acquisitionMatrixDimension1D = userMatrixDimension1D;

        double spectralWidthPerPixel = getDouble(SPECTRAL_WIDTH_PER_PIXEL);
        spectralWidth = isSW ? spectralWidth : spectralWidthPerPixel * acquisitionMatrixDimension1D;

        spectralWidth = Hardware.getNearestSpectralWidth(spectralWidth);      // get real spectral width from Chameleon
        double spectralWidthUP = spectralWidth;
        spectralWidthPerPixel = spectralWidth / acquisitionMatrixDimension1D;
        getParam(SPECTRAL_WIDTH_PER_PIXEL).setValue(spectralWidthPerPixel);
        getParam(SPECTRAL_WIDTH).setValue(spectralWidthUP);
        observation_time = acquisitionMatrixDimension1D / spectralWidth;
        getParam(ACQUISITION_TIME_PER_SCAN).setValue(observation_time);   // display observation time
        getParam(DWELL_TIME).setValue(observation_time / acquisitionMatrixDimension1D);   // display observation time

        // -----------------------------------------------
        // 2nd D management
        // -----------------------------------------------
        // MATRIX

        if (!(nutationType == NutationType.None)) {
            // check if the starting point is inferior to the ending point
            if (txLengthMin > txLengthMax) {
                txLengthMin = txLengthMax;
                getParam(TX_NUTATION_LENGTH_MIN).setValue(txLengthMin);
                getParam(TX_NUT_STEP_NUMBER).setValue(1);
            }
            if (txAmpMin > txAmpMax) {
                txAmpMin = txAmpMax;
                getParam(TX_NUTATION_AMP_MIN).setValue(txAmpMin);
                getParam(TX_NUT_STEP_NUMBER).setValue(1);
            }
            if (txVoltMin > txVoltMax) {
                txVoltMin = txVoltMax;
                getParam(TX_NUTATION_VOLT_MIN).setValue(txVoltMin);
                getParam(TX_NUT_STEP_NUMBER).setValue(1);
            }
            // solve conflict between power input parameters and prepare params to solve power for maximum value
            if (powerInput != PowerInput.FA) {
                if (nutationType == NutationType.Amplitude) {
                    getParam(TX_AMP).setValue(txAmpMax);
                    powerInput = PowerInput.AmpAtt;
                    getParam(TX_POWER_INPUT).setValue("Att/Amp");
                } else if (nutationType == NutationType.Voltage) {
                    txVolt = getDouble(TX_NUTATION_VOLT_MAX);
                    getParam(TX_VOLTAGE).setValue(txVolt);
                    powerInput = PowerInput.Volt;
                    getParam(TX_POWER_INPUT).setValue("Voltage");
                }
            }

            acquisitionMatrixDimension2D = getInt(TX_NUT_STEP_NUMBER);
            getParam(USER_MATRIX_DIMENSION_2D).setValue(acquisitionMatrixDimension2D);
            nb_scan_2d = acquisitionMatrixDimension2D;

            isMultiplanar = nutationType != NutationType.Length && isMultiplanar;
            getParam(MULTI_PLANAR_EXCITATION).setValue(isMultiplanar);

            isDelayEcho = nutationType != NutationType.Length && isDelayEcho;
            getParam(DELAY_ECHO).setValue(isDelayEcho);

        } else {
            acquisitionMatrixDimension2D = userMatrixDimension2D;
            nb_scan_2d = userMatrixDimension2D;
        }
        // -----------------------------------------------
        // 3D management
        // ------------------------------------------------
        // MATRIX
        acquisitionMatrixDimension3D = userMatrixDimension3D;
        nb_scan_3d = userMatrixDimension3D;

        // -----------------------------------------------
        // 4D managment:  Dynamic, MultiEcho, External triggering
        // -----------------------------------------------   

        nb_scan_4d = numberOfTrigger;
        acquisitionMatrixDimension4D = nb_scan_4d;
        getParam(USER_MATRIX_DIMENSION_4D).setValue(nb_scan_4d);

        // -----------------------------------------------
        // set the ACQUISITION_MATRIX and Nb XD
        // -----------------------------------------------        // set the calculated acquisition matrix
        getParam(ACQUISITION_MATRIX_DIMENSION_1D).setValue(acquisitionMatrixDimension1D);
        getParam(ACQUISITION_MATRIX_DIMENSION_2D).setValue(acquisitionMatrixDimension2D);
        getParam(ACQUISITION_MATRIX_DIMENSION_3D).setValue(acquisitionMatrixDimension3D);
        getParam(ACQUISITION_MATRIX_DIMENSION_4D).setValue(acquisitionMatrixDimension4D);

        // set the calculated sequence dimensions 
        set(Pre_scan, DUMMY_SCAN); // Do the prescan
        set(Nb_point, acquisitionMatrixDimension1D);
        set(Nb_1d, NUMBER_OF_AVERAGES);
        set(Nb_2d, nb_scan_2d);
        set(Nb_3d, nb_scan_3d);
        set(Nb_4d, nb_scan_4d);

        // -----------------------------------------------
        // Image Orientation
        // -----------------------------------------------
        //READ PHASE and SLICE matrix
        //Offset according to animal position
        off_center_distance_3D = getDouble(OFF_CENTER_FIELD_OF_VIEW_3D);
        off_center_distance_1D = getOff_center_distance_1D_2D_3D(1);
        off_center_distance_2D = getOff_center_distance_1D_2D_3D(2);
        off_center_distance_3D = getOff_center_distance_1D_2D_3D(3);

        if (!isMultiplanar) {
            off_center_distance_3D = 0;
        }
        off_center_distance_1D = 0;
        off_center_distance_2D = 0;
        getParam(OFF_CENTER_FIELD_OF_VIEW_3D).setValue(off_center_distance_3D);
        getParam(OFF_CENTER_FIELD_OF_VIEW_2D).setValue(off_center_distance_2D);
        getParam(OFF_CENTER_FIELD_OF_VIEW_1D).setValue(off_center_distance_1D);

        ArrayList<Number> off_center_distanceList = new ArrayList<>();
        off_center_distanceList.add(0);
        off_center_distanceList.add(0);
        off_center_distanceList.add(off_center_distance_3D);

        getParam(OFF_CENTER_FIELD_OF_VIEW_EFF).setValue(off_center_distanceList);
        // ------------------------------------------------------------------
        // load preemphasis
        // ------------------------------------------------------------------

        // -----------------------------------------------
        // activate gradient rotation matrix
        // -----------------------------------------------
        GradientRotation.setSequenceGradientRotation(this);
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
        set(enabled_slice, isMultiplanar);
        set(enabled_spoiler, GRADIENT_SPOILER_ACTIVATE);
        // -----------------------------------------------
        // calculate gradient equivalent rise time
        // -----------------------------------------------
        double grad_rise_time = getDouble(GRADIENT_RISE_TIME);
        double min_rise_time_factor = getDouble(MIN_RISE_TIME_FACTOR);

        double min_rise_time_sinus = GradientMath.getShortestRiseTime(100.0) * Math.PI / 2 * 100 / min_rise_time_factor;

        if (grad_rise_time < min_rise_time_sinus) {
            double new_grad_rise_time = ceilToSubDecimal(min_rise_time_sinus, 5);
            getUnreachParamExceptionManager().addParam(GRADIENT_RISE_TIME.name(), grad_rise_time, new_grad_rise_time, ((NumberParam) getParam(GRADIENT_RISE_TIME)).getMaxValue(), "Gradient ramp time too short ");
            grad_rise_time = new_grad_rise_time;
        }
        setSequenceTableSingleValue(Time_grad_ramp, grad_rise_time);

        // -----------------------------------------------
        // Calculation RF pulse parameters  1/3 : Shape
        // -----------------------------------------------
        Table txLengthTable = setSequenceTableValues(Time_tx, Order.Two);
        txLengthTable.add(txLength);

        RFPulse pulseTX = RFPulse.createRFPulse(getSequence(), Tx_att, Tx_amp, Tx_phase, Time_tx, Tx_shape, Tx_shape_phase, Tx_freq_offset, nucleus);

        int nb_shape_points = 128;
        pulseTX.setShape((getText(TX_SHAPE)), nb_shape_points, "Hamming");

        // -----------------------------------------------
        // Calculation RF pulse parameters  2/3 : Maximum power check and RF pulse amp & attenuation
        // -----------------------------------------------
        int tx_att;
        double tx_amp;
        double flip_angle = getDouble(FLIP_ANGLE);

        // Check power and prepare tx Att/amp: the way Att/Amp are prepared depends on which option is selected
        if (powerInput == PowerInput.FA && nutationType != NutationType.Length) {
            if (nutationType == NutationType.None) {
                if (!pulseTX.solveOnePulseWithFlipAngleAndReference180(flip_angle, 80, observeFrequency, getListInt(TX_ROUTE))) {
                    getUnreachParamExceptionManager().addParam(TX_LENGTH.name(), txLength, pulseTX.getPulseDuration(), ((NumberParam) getParam(TX_LENGTH)).getMaxValue(), "Pulse length too short to reach RF power with this pulse shape");
                    txLength = pulseTX.getPulseDuration();
                }
            } else { // nutation volt or amp att
                if (!pulseTX.prepPower(flip_angle, observeFrequency)) {
                    getUnreachParamExceptionManager().addParam(TX_LENGTH.name(), txLength, pulseTX.getPulseDuration(), ((NumberParam) getParam(TX_LENGTH)).getMaxValue(), "Pulse length too short to reach RF power with this pulse shape");
                    txLength = pulseTX.getPulseDuration();
                }
                pulseTX.prepChannelAttWithReferencePower(pulseTX.getPower(), 100, getListInt(TX_ROUTE));
                pulseTX.prepTxAmp(getListInt(TX_ROUTE));
            }
        } else if (powerInput == PowerInput.Volt) {
            if (!pulseTX.solveOnePulseWithVoltage(txVolt, 80, observeFrequency, getListInt(TX_ROUTE))) {
                getUnreachParamExceptionManager().addParam(TX_VOLTAGE.name(), txVolt, ((NumberParam) getParam(TX_VOLTAGE)).getMinValue(), pulseTX.getVoltage(), "Pulse voltage too high for RF coil");
                txVolt = pulseTX.getVoltage();
            }
        } else { // power input Amp/Att or FA+nutation length
            tx_amp = getDouble(TX_AMP);
            tx_att = getInt(TX_ATT);
            if (!pulseTX.solveOnePulseWithAmpAtt(tx_amp, tx_att, observeFrequency, getListInt(TX_ROUTE))) {
                getUnreachParamExceptionManager().addParam(TX_ATT.name(), tx_att, pulseTX.getAtt(), ((NumberParam) getParam(TX_ATT)).getMaxValue(), "Pulse attenuation too low for RF coil");
                getUnreachParamExceptionManager().addParam(TX_AMP.name(), tx_amp, ((NumberParam) getParam(TX_AMP)).getMinValue(), pulseTX.getAmp(), "Pulse amplitude too high for RF coil");
            }
        }
        tx_amp = pulseTX.getAmp();
        tx_att = pulseTX.getAtt();
        txVolt = pulseTX.getVoltage();

        //  finalise pulse or nutation amplitude
        set(Tx_blanking, tx_amp != 0 || !(nutationType == NutationType.None));

        // Print information for debug only
        // ------------------------------------------------------------------------------------------------------------
        if (getBoolean(DEBUG_MODE)) {
            // Calibrations
            double instrument_length = PowerComputation.getHardPulse90Width(nucleus.name());
            double power_90 = PowerComputation.getHardPulse90Power(nucleus.name()) / pulseTX.getShapePowerFactor90();

            double ampSP = pulseTX.getAmpTable().get(0).doubleValue();
            int attSP = pulseTX.getAttParam().intValue();
            //Recompute power and flip angle directly from the SP
            double powSP = PowerComputation.getPower(getListInt(TX_ROUTE).get(0), observeFrequency, ampSP, attSP);
            double timeSP = pulseTX.getTimeTable().get(0).doubleValue();
            double FASP = 90 * Math.sqrt(powSP / power_90) * timeSP / instrument_length;

            // Recompute power from UP
            double timeUP = getDouble(TX_LENGTH);
            double powUP = power_90 * Math.pow(flip_angle / 180, 2) * Math.pow(instrument_length / timeUP, 2);

            System.out.println(" ");
            System.out.println("------------- check pulses preparation -------------"); //check if the value written in RFPulse and SP are match with prescription
            System.out.println("1/ Parameter value from: RFPulse, the sequence (SP):");
            System.out.printf("Amplitude: %n %f     %f %n", tx_amp, ampSP);
            System.out.printf("Attenuation: %n %d   %d %n", tx_att, attSP);
            System.out.printf("Length: %n %f    %f %n", pulseTX.getPulseDuration(), timeSP);
            System.out.println("2/ Parameter value from: UP, RFPulse, SP, error UP(ref) vs RFPulse, error SP(ref) vs RFPulse");
            System.out.printf("Power: %n %f    %f     %f     %f    %f %n",
                    powUP, pulseTX.getPower(), powSP, Math.abs(pulseTX.getPower() - powUP) * 100 / pulseTX.getPower(), Math.abs(pulseTX.getPower() - powSP) * 100 / pulseTX.getPower());
            System.out.printf("Flip angle: %n %f    %f     %f     %f    %f %n",
                    flip_angle, pulseTX.getFlipAngle(), FASP, Math.abs(pulseTX.getFlipAngle() - flip_angle) * 100 / pulseTX.getFlipAngle(), Math.abs(pulseTX.getFlipAngle() - FASP) * 100 / pulseTX.getFlipAngle());
            System.out.println(" ");
        }

        // Nutation curves
        // ------------------------------------------------------------------------------------------------------------
        ArrayList<Number> list_tx_amps = new ArrayList<>();
        ArrayList<Number> list_tx_volts = new ArrayList<>();
        ArrayList<Number> list_tx_length = new ArrayList<>();

        double[] tx_amps = new double[acquisitionMatrixDimension2D];
        if (nutationType == NutationType.Amplitude) {
            double tx_amp_step;
            if (powerInput != PowerInput.FA) {
                // Maximum amplitude has previously been prepared and checked, if the value of tx_amp was changed because of power limit exceed the tx_amp_start and end should be adjusted accordingly
                // Check that amplitude doesn't exceed maximum amplitude achievable
                if (txAmpMin > tx_amp) {
                    getUnreachParamExceptionManager().addParam(TX_NUTATION_AMP_MIN.name(), txAmpMin, ((NumberParam) getParam(TX_NUTATION_AMP_MIN)).getMinValue(), tx_amp, "Amplitude values exceed power limit: the maximum amplitude achievable with this attenuation is " + ceilToSubDecimal(tx_amp, 2) + "%");
                    getParam(TX_NUT_STEP_NUMBER).setValue(1);
                    getParam(ACQUISITION_MATRIX_DIMENSION_2D).setValue(1);
                } else if ((txAmpMin < tx_amp) && (txAmpMax > tx_amp)) {
                    getUnreachParamExceptionManager().addParam(TX_NUTATION_AMP_MAX.name(), txAmpMax, ((NumberParam) getParam(TX_NUTATION_AMP_MAX)).getMinValue(), tx_amp, "Amplitude values exceed power limit: the maximum amplitude achievable with this attenuation is " + ceilToSubDecimal(tx_amp, 2) + "%");
                    txAmpMax = tx_amp;
                }
                tx_amp_step = acquisitionMatrixDimension2D == 1 ? 0 : (txAmpMax - txAmpMin) / (acquisitionMatrixDimension2D - 1);
            } else {
                txAmpMin = 0;
                txAmpMax = tx_amp;
                tx_amp_step = txAmpMax / (acquisitionMatrixDimension2D - 1);
                getParam(TX_NUTATION_AMP_MIN).setValue(txAmpMin);
                getParam(TX_NUTATION_AMP_MAX).setValue(txAmpMax);
            }
            tx_amp_step = (int) (tx_amp_step / txAmpMinResolution) * txAmpMinResolution;

            // create and set amplitude table (the pulse has already been solved for the maximum amplitude)
            double voltage_tmp;
            double power_tmp;
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                tx_amps[i] = (txAmpMin + i * tx_amp_step);
                power_tmp = PowerComputation.getPower(getListInt(TX_ROUTE).get(0), observeFrequency, tx_amps[i], tx_att);
                voltage_tmp = RFPulse.wattToVPP(power_tmp);
                if (i == 0) txVoltMin = voltage_tmp;
                if (i == acquisitionMatrixDimension2D) txVoltMax = voltage_tmp;
                list_tx_volts.add(voltage_tmp);
                list_tx_amps.add(tx_amps[i]);
            }
            pulseTX.setAmp(Order.Two, tx_amps);


        } else if (nutationType == NutationType.Voltage) {
            double tx_volt_step;
            if (powerInput != PowerInput.FA) {
                // prepare length sampling ramp: first value, last value and incremental step
                // Maximum amplitude has previously been prepared and checked, if the value of tx_amp was changed because of power limit exceed the tx_amp_start and end should be adjusted accordingly
                // Check that amplitude doesn't exceed maximum amplitude achievable
                if (txVoltMin > txVolt) {
                    getUnreachParamExceptionManager().addParam(TX_NUTATION_VOLT_MIN.name(), txVoltMin, ((NumberParam) getParam(TX_NUTATION_VOLT_MIN)).getMinValue(), txVolt, "Voltage values exceed power limit: the maximum voltage achievable is " + ceilToSubDecimal(txVolt, 2) + "V");
                    getParam(TX_NUT_STEP_NUMBER).setValue(1);
                    getParam(ACQUISITION_MATRIX_DIMENSION_2D).setValue(1);
                } else if ((txVoltMin < txVolt) && (txVoltMax > txVolt)) {
                    getUnreachParamExceptionManager().addParam(TX_NUTATION_VOLT_MAX.name(), txVoltMax, ((NumberParam) getParam(TX_NUTATION_VOLT_MAX)).getMinValue(), txVolt, "Voltage values exceed power limit: the maximum voltage is " + ceilToSubDecimal(txVolt, 2) + "V");
                    txVoltMax = txVolt;
                }
                tx_volt_step = acquisitionMatrixDimension2D == 1 ? 0 : (txVoltMax - txVoltMin) / (acquisitionMatrixDimension2D - 1);
            } else {
                txVoltMin = 0;
                txVoltMax = txVolt;
                tx_volt_step = txVoltMax / (acquisitionMatrixDimension2D - 1);
            }
            tx_volt_step = (int) (tx_volt_step / txVoltMinResolution) * txVoltMinResolution;

            // Prepare amplitude and voltage arrays
            // create and set amplitude table (the pulse has already been solved for the maximum amplitude)
            double voltage_tmp;
            double power_tmp;
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                voltage_tmp = (txVoltMin + i * tx_volt_step);
                power_tmp = RFPulse.voltPPToWatt(voltage_tmp);
                tx_amps[i] = PowerComputation.getTxAmplitude(getListInt(TX_ROUTE).get(0), power_tmp, observeFrequency, tx_att);
                list_tx_volts.add(voltage_tmp);
                list_tx_amps.add(tx_amps[i]);
                //              System.out.println(tx_amps[i]);
            }
            pulseTX.setAmp(Order.Two, tx_amps);
            txAmpMin = tx_amps[0];
            txAmpMax = tx_amps[acquisitionMatrixDimension2D - 1];
        } else {
            list_tx_amps.add(tx_amp);
            list_tx_volts.add(txVolt);
        }

        double[] tx_lengths = new double[acquisitionMatrixDimension2D];
        if (nutationType == NutationType.Length) {
            Order order = txLengthTable.getOrder();
            txLengthTable.clear();
            txLengthTable.setOrder(order);
            // prepare length sampling ramp: first value, last value and incremental step
            double tx_step;
            if (powerInput != PowerInput.FA) {
                tx_step = acquisitionMatrixDimension2D == 1 ? 0 : (txLengthMax - txLengthMin) / (acquisitionMatrixDimension2D - 1);
            } else {
                txLengthMin = 0;
                txLengthMax = txLength * flip_angle / pulseTX.getFlipAngle();
                tx_step = txLengthMax / (acquisitionMatrixDimension2D - 1);
                getParam(TX_NUTATION_LENGTH_MIN).setValue(txLengthMin);
                getParam(TX_NUTATION_LENGTH_MAX).setValue(txLengthMax);
            }
            // the step have to be a multiple of the time hardware resolution to be correctly sampled
            tx_step = (int) (tx_step / txLengthMinResolution) * txLengthMinResolution;

            // Prepare length array
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                tx_lengths[i] = (powerInput == PowerInput.FA && i == 0) ? txLengthMin + tx_step : (txLengthMin + i * tx_step);
                if (powerInput == PowerInput.FA) {
                    tx_amps[i] = i == 0 ? 0 : tx_amp;
                }
                txLengthTable.add(tx_lengths[i]);
                list_tx_length.add(roundToDecimal((powerInput == PowerInput.FA && i == 0) ? 0 : tx_lengths[i], 6));
            }
            txLengthMax = tx_lengths[acquisitionMatrixDimension2D - 1];
            getParam(TX_LENGTH).setValue(txLengthMax);
        } else {
            txLengthMax = txLength;
            list_tx_length.add(txLength);
        }
        if (nutationType != NutationType.None && powerInput == PowerInput.FA)
            pulseTX.setAmp(Order.Two, tx_amps); // for nutation and automatic setting using FA, amplitude is always variable

        // Write UP Values
        // ------------------------------------------------------------------------------------------------------------
        // force voltage and power to 0 when amplitude is 0 for clarity (otherwise the function will return a small value around 0)
        getParam(TX_AMP).setValue(tx_amp);
        getParam(TX_ATT).setValue(tx_att);
        getParam(TX_VOLTAGE).setValue(tx_amp == 0 ? 0 : pulseTX.getVoltage());
        getParam(TX_POWER).setValue(tx_amp == 0 ? 0 : ceilToSubDecimal(pulseTX.getPower(), 5));
        getParam(TX_GAMMA_B1).setValue(Math.round(pulseTX.getPowerGammaB1()));
        if (powerInput != PowerInput.FA) //in auto mode, Att/Amp are computed from FA so update FA can propagate errors if sequence is run more than one time
            this.getParam(FLIP_ANGLE).setValue(Math.round(pulseTX.getFlipAngle()));
        getParam(TX_NUTATION_AMP_VALUES).setValue(list_tx_amps);
        getParam(TX_NUTATION_AMP_MIN).setValue(txAmpMin);
        getParam(TX_NUTATION_AMP_MAX).setValue(txAmpMax);
        getParam(TX_NUTATION_VOLT_VALUES).setValue(list_tx_volts);
        getParam(TX_NUTATION_VOLT_MIN).setValue(txVoltMin);
        getParam(TX_NUTATION_VOLT_MAX).setValue(txVoltMax);
        getParam(TX_NUTATION_LENGTH_VALUES).setValue(list_tx_length);
        getParam(TX_NUTATION_LENGTH_MIN).setValue(txLengthMin);
        getParam(TX_NUTATION_LENGTH_MAX).setValue(roundToDecimal(txLengthMax, 6));
        // -----------------------------------------------
        // Calculation RF pulse parameters  3/3: bandwidth
        // -----------------------------------------------
        double tx_bandwidth_factor;
        if ("GAUSSIAN".equalsIgnoreCase(getText(TX_SHAPE))) {
            tx_bandwidth_factor = 1.35;
        } else if ("SINC3".equalsIgnoreCase(getText(TX_SHAPE))) {
            tx_bandwidth_factor = 2.55;
        } else if ("SINC5".equalsIgnoreCase(getText(TX_SHAPE))) {
            tx_bandwidth_factor = 4.25;
        } else {
            tx_bandwidth_factor = 0.95;
        }
        double tx_bandwidth = tx_bandwidth_factor / txLength;

        // ---------------------------------------------------------------------
        // calculate SLICE gradient amplitudes for RF pulses
        // ---------------------------------------------------------------------
        double slice_thickness_excitation = (sliceThickness);
        setSequenceTableSingleValue(Time_grad_ramp, isMultiplanar ? grad_rise_time : minInstructionDelay);
        double blanking_time = Hardware.getRfAmplifierChannelBlankingDelay(getListInt(TX_ROUTE).get(0));
        setSequenceTableSingleValue(Time_grad_ramp_blanking, Math.max(isMultiplanar ? grad_rise_time : minInstructionDelay, blanking_time));
// 15 40 2.5m

        Gradient gradSlice = Gradient.createGradient(getSequence(), Grad_amp_slice, Time_tx, Grad_shape_up, Grad_shape_down, Time_grad_ramp, nucleus);
        if (isMultiplanar && !gradSlice.prepareSliceSelection(tx_bandwidth, slice_thickness_excitation)) {
            slice_thickness_excitation = gradSlice.getSliceThickness();
            double slice_thickness_min = (isMultiplanar ? slice_thickness_excitation : (slice_thickness_excitation / userMatrixDimension3D));
            getUnreachParamExceptionManager().addParam(SLICE_THICKNESS.name(), sliceThickness, slice_thickness_min, ((NumberParam) getParam(SLICE_THICKNESS)).getMaxValue(), "Pulse length too short to reach this slice thickness");
            sliceThickness = slice_thickness_min;
        }
        gradSlice.applyAmplitude();

        // calculate SLICE_refocusing
        double grad_ref_application_time = getDouble(GRADIENT_REFOC_TIME);
        setSequenceTableSingleValue(Time_grad_ref, isMultiplanar ? grad_ref_application_time : minInstructionDelay);
        Gradient gradSliceRefPhase3D = Gradient.createGradient(getSequence(), Grad_amp_slice_ref, Time_grad_ref, Grad_shape_up, Grad_shape_down, Time_grad_ramp, nucleus);
        if (isMultiplanar) {
            gradSliceRefPhase3D.refocalizeGradient(gradSlice, 0.5);
        }
        gradSliceRefPhase3D.applyAmplitude();

        // -----------------------------------------------
        // calculate ADC observation time
        // -----------------------------------------------
        setSequenceTableSingleValue(Time_rx, observation_time);
        set(Spectral_width, spectralWidth);
        set(LO_att, Hardware.getLoAttenuation());

        // ---------------------------------------------------------------------
        // calculate SPOILER gradient amplitudes
        // ---------------------------------------------------------------------
        boolean is_spoiler = getBoolean(GRADIENT_SPOILER_ACTIVATE);
        double grad_spoiler_application_time = getDouble(GRADIENT_SPOILER_TIME);

        setSequenceTableSingleValue(Time_grad_spoil, is_spoiler ? grad_spoiler_application_time : minInstructionDelay);

        setSequenceTableSingleValue(Time_grad_spoil_ramp, is_spoiler ? grad_rise_time : minInstructionDelay);

        double grad_spoiler_amp = getDouble(GRADIENT_SPOILER_AMP);

        Gradient gradSpoiler = Gradient.createGradient(getSequence(), Grad_amp_spoiler, Time_grad_spoil, Grad_shape_up, Grad_shape_down, Time_grad_spoil_ramp, nucleus);
        if (getBoolean(GRADIENT_SPOILER_ACTIVATE))
            gradSpoiler.addSpoiler(grad_spoiler_amp);
        gradSpoiler.applyAmplitude();

        // --------------------------------------------------------------------------------------------------------------------------------------------
        // TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING --- TIMING
        // --------------------------------------------------------------------------------------------------------------------------------------------
        //
        //                                                          index of sequence events
        //
        // --------------------------------------------------------------------------------------------------------------------------------------------

        Events.checkEventShortcut(getSequence());
        // ------------------------------------------
        // calculate delays adapted to current TE & search for incoherence
        // ------------------------------------------
        // calculate actual delays between Rf-pulses and ADC
        double time0 = TimeEvents.getTimeBetweenEvents(getSequence(), Events.Pulse.ID + 1, Events.Delay.ID - 1) + TimeEvents.getTimeBetweenEvents(getSequence(), Events.Delay.ID + 1, Events.Acq.ID - 1);
        double time1 = time0 + txLengthMax / 2;// Actual_TE

        // get minimal TE value & search for incoherence
        double max_time = ceilToSubDecimal(time1, 5);
        double te_min = max_time + minInstructionDelay;

        Table time_te_delay_table = setSequenceTableValues(Time_te_delay, Order.Two);

        boolean isDelayEcho = (getBoolean(DELAY_ECHO));
        double delay1 = Math.max(minInstructionDelay, getDouble(DELAY_ECHO_TIME));
        if (isDelayEcho) {
            te = delay1 + time1;
            getParam(ECHO_TIME).setValue(te);

            delay1 = te - time1;
            time_te_delay_table.add(delay1);
        } else {
            if (te < te_min) {
                te_min = ceilToSubDecimal(te_min, 5);
                getUnreachParamExceptionManager().addParam(ECHO_TIME.name(), te, te_min, ((NumberParam) getParam(ECHO_TIME)).getMaxValue(), "Tx-Rx Time too short for the User Mx1D and SW");
                te = te_min;//
            }
            if (nutationType == NutationType.Length) {
                for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                    time_te_delay_table.add(te - time0 - tx_lengths[i] / 2);
                }
            } else {
                delay1 = te - time1;
                time_te_delay_table.add(delay1);
                getParam(DELAY_ECHO_TIME).setValue(delay1);
            }
        }
        // set calculated the pulseDuration delays to get the proper TE


        // ------------------------------------------
        // delays for FIR
        // ------------------------------------------

        boolean is_FIR = Hardware.isRemoveAcquisitionDeadPoints();
        double lo_FIR_dead_point = is_FIR ? Hardware.getNbAcquisitionDeadPoints() : 0;
        double min_FIR_delay = (lo_FIR_dead_point + 2) / spectralWidth;
        double min_FIR_4pts_delay = 4 / spectralWidth;

        double time_fir = TimeEvents.getTimeBetweenEvents(getSequence(), Events.Acq.ID + 1, Events.End.ID - 1);
        time_fir -= TimeEvents.getTimeForEvents(getSequence(), Events.FIRDelay.ID); // Actual_TE without delay1
        System.out.println("time_fir " + time_fir);
        double time_fir_delay = Math.max(minInstructionDelay, min_FIR_4pts_delay - time_fir);
        time_fir = TimeEvents.getTimeBetweenEvents(getSequence(), Events.Acq.ID + 1, Events.End.ID - 1);

        setSequenceTableSingleValue(Time_FIR_delay, time_fir_delay);

        //--------------------------------------------------------------------------------------
        //  External triggering
        //--------------------------------------------------------------------------------------
        getSequenceParam(Synchro_trigger).setValue(isTrigger ? TimeElement.Trigger.External : TimeElement.Trigger.Timer);
        getSequenceParam(Synchro_trigger).setLocked(true);

        double time_external_trigger_delay_max = minInstructionDelay;

        Table triggerdelay = setSequenceTableValues(Time_trigger_delay, Order.Four);
        if ((!isTrigger)) {
            triggerdelay.add(minInstructionDelay);
        } else {
            for (int i = 0; i < numberOfTrigger; i++) {
                double time_external_trigger_delay = roundToDecimal(triggerTime.get(i), 7);
                time_external_trigger_delay = Math.max(time_external_trigger_delay, minInstructionDelay);
                triggerdelay.add(time_external_trigger_delay);
                time_external_trigger_delay_max = Math.max(time_external_trigger_delay_max, time_external_trigger_delay);
            }
        }

        set(Ext_trig_source, TRIGGER_CHANEL);
        // ---------------------------------------------------------------
        // calculate TR , Time_last_delay  Time_TR_delay & search for incoherence
        // ---------------------------------------------------------------
        double min_flush_delay = Math.max(min_FIR_delay - time_fir, minInstructionDelay); // minimal time to flush Chameleon buffer (this time is doubled to avoid hidden delays);

        double time_seq_to_end_spoiler0 = TimeEvents.getTimeBetweenEvents(getSequence(), Events.Start.ID, Events.Pulse.ID - 1) + te + TimeEvents.getTimeBetweenEvents(getSequence(), Events.Acq.ID, Events.End.ID - 1);
        double time_seq_to_end_spoiler = time_seq_to_end_spoiler0 + txLengthMax / 2;
        double tr_min = time_seq_to_end_spoiler + min_flush_delay;// 2 +( 2 minInstructionDelay: event 22 +(20&21
        if (tr < tr_min) {
            System.out.println(tr + " < " + tr_min);
            tr_min = Math.ceil(tr_min * Math.pow(10, 4)) / Math.pow(10, 4);
            notifyOutOfRangeParam(REPETITION_TIME, tr_min, ((NumberParam) getParam(REPETITION_TIME)).getMaxValue(), "TR too short to reach");
            tr = tr_min;
        }
        System.out.println(" tr =  " + tr);
        // ------------------------------------------
        // set calculated TR
        // ------------------------------------------
        // set  TR delay to compensate and trigger delays
        Table time_last_delay_table = setSequenceTableValues(Time_last_delay, Order.Two);
        if (nutationType == NutationType.Length) {
            for (int i = 0; i < acquisitionMatrixDimension2D; i++) {
                time_last_delay_table.add(tr - time_seq_to_end_spoiler0 - tx_lengths[i] / 2);
            }
        } else {
            double last_delay = tr - time_seq_to_end_spoiler;
            time_last_delay_table.add(last_delay);
        }


        double total_acquisition_time = tr * nb_scan_4d * nb_scan_3d * nb_scan_2d * nb_scan_1d;
        getParam(SEQUENCE_TIME).setValue(total_acquisition_time);
        // -----------------------------------------------
        // Phase Reset
        // -----------------------------------------------
        set(Phase_reset, PHASE_RESET);

        // ------------------------------------------------------------------
        // calculate TX FREQUENCY offsets tables for multi-slice acquisitions and
        // ------------------------------------------------------------------
        double grad_amp_slice_mTpm = (tx_bandwidth / ((GradientMath.GAMMA) * slice_thickness_excitation));
        double frequency_center_3D_90 = -grad_amp_slice_mTpm * off_center_distance_3D * (GradientMath.GAMMA);
        if (!isMultiplanar) {
            frequency_center_3D_90 = 0;
        }
        setSequenceTableSingleValue(Tx_freq_offset, frequency_center_3D_90);

    }


    private double ceilToSubDecimal(double numberToBeRounded, double order) {
        return Math.ceil(numberToBeRounded * Math.pow(10, order)) / Math.pow(10, order);
    }

    private double roundToDecimal(double numberToBeRounded, double order) {
        return Math.round(numberToBeRounded * Math.pow(10, order)) / Math.pow(10, order);
    }


    private Table setSequenceTableSingleValue(S tableName, double... values) {
        // uses Order.One because there are no tables in this dimension: compilation issue
        return setSequenceTableValues(tableName, Order.FourLoop, values);
    }

    private Table setSequenceTableValues(S tableName, Order order, double... values) {
        Table table = getSequenceTable(tableName);
        table.clear();
        table.setOrder(order);
        table.setLocked(true);

        for (double value : values) {
            table.add(value);
        }
        return table;
    }

    private double getOff_center_distance_1D_2D_3D(int dim) {
        List<Double> image_orientation = getListDouble(IMAGE_ORIENTATION_SUBJECT);
        double[] direction_index = new double[9];
        direction_index[0] = image_orientation.get(0);
        direction_index[1] = image_orientation.get(1);
        direction_index[2] = image_orientation.get(2);
        direction_index[3] = image_orientation.get(3);
        direction_index[4] = image_orientation.get(4);
        direction_index[5] = image_orientation.get(5);
        direction_index[6] = direction_index[1] * direction_index[5] - direction_index[2] * direction_index[4];
        direction_index[7] = direction_index[2] * direction_index[3] - direction_index[0] * direction_index[5];
        direction_index[8] = direction_index[0] * direction_index[4] - direction_index[1] * direction_index[3];

        double norm_vector_read = Math.sqrt(Math.pow(direction_index[0], 2) + Math.pow(direction_index[1], 2) + Math.pow(direction_index[2], 2));
        double norm_vector_phase = Math.sqrt(Math.pow(direction_index[3], 2) + Math.pow(direction_index[4], 2) + Math.pow(direction_index[5], 2));
        double norm_vector_slice = Math.sqrt(Math.pow(direction_index[6], 2) + Math.pow(direction_index[7], 2) + Math.pow(direction_index[8], 2));

        //Offset according to animal position
        double off_center_distance_Z = getDouble(OFF_CENTER_FIELD_OF_VIEW_Z);
        double off_center_distance_Y = getDouble(OFF_CENTER_FIELD_OF_VIEW_Y);
        double off_center_distance_X = getDouble(OFF_CENTER_FIELD_OF_VIEW_X);

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


    //<editor-fold defaultstate="collapsed" desc="Generated Code (RS2D)">
    protected void addUserParams() {
        addMissingUserParams(U.values());
    }

    public String getName() {
        return "OnePulse_Grad_Slc";
    }

    public String getVersion() {
        return "master";
    }
    //</editor-fold>


}