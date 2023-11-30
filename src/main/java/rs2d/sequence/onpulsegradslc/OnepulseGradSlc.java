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
import rs2d.spinlab.instrument.Instrument;
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

    private final String sequenceVersion = "Version1.2";
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
    private double minInstructionDelay = 0.000005;     // single instruction minimal duration
    private final double txAmpMinResolution = 0.01;
    private final double txVoltMinResolution = 0.01;
    private final double txLengthMinResolution = 128 * Math.pow(10, -9);
    private double gradFreq = 78.125 * 11 / (35 * 128) * 1000000;
    private double gradFreq11 = 78.125 / (35 * 128) * 1000000;

    private boolean isGradClocked;
    int gradClockNumber = 11;

    private String calibShape = "";
    private double gMaxSeq =0.0;

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

        txLength = getDouble(TX_LENGTH); //reference for calculation of FA ref and check power

        isTrigger = getBoolean(TRIGGER_EXTERNAL);
        triggerTime = getListDouble(TRIGGER_TIME);
        numberOfTrigger = isTrigger ? triggerTime.size() : 1;
        isTrigger = isTrigger && (numberOfTrigger >= 1);


        observation_time = getDouble(ACQUISITION_TIME_PER_SCAN);
        isGradClocked = getBoolean(GRAD_CLOCK);

        calibShape = getText(CALIB_GRAD_SHAPE);
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

        spectralWidth = isGradClocked? roundSWToGradClock(spectralWidth, acquisitionMatrixDimension1D) : Hardware.getNearestSpectralWidth(spectralWidth);      // get real spectral width from Chameleon
        double spectralWidthUP = spectralWidth;
        System.out.println("dwell time " + 1/spectralWidth);
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
        if (userMatrixDimension2D > 3){
            userMatrixDimension2D = 3;
            getParam(USER_MATRIX_DIMENSION_2D).setValue(3);
        }
        acquisitionMatrixDimension2D = userMatrixDimension2D;
        nb_scan_2d = userMatrixDimension2D;

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

        getParam(GMAX).setValue(GradientMath.getMaxGradientStrength());
        // -----------------------------------------------
        // enable gradient lines
        // -----------------------------------------------
        set(enabled_slice, isMultiplanar);
        set(enabled_spoiler, GRADIENT_SPOILER_ACTIVATE);
        minInstructionDelay = isGradClocked ? ceilToGradClock(minInstructionDelay, gradClockNumber) : minInstructionDelay;
        // -----------------------------------------------
        // calculate gradient equivalent rise time
        // -----------------------------------------------
        double grad_rise_time = isGradClocked? ceilToGradClock(getDouble(GRADIENT_RISE_TIME), gradClockNumber): getDouble(GRADIENT_RISE_TIME);
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
        Table txLengthTable = setSequenceTableSingleValue(Time_tx);
        if(isGradClocked){txLength = ceilToGradClock(txLength, gradClockNumber);}
        getParam(TX_LENGTH).setValue(txLength);
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
        if (powerInput == PowerInput.FA) {
                if (!pulseTX.solveOnePulseWithFlipAngleAndReference180(flip_angle, 80, observeFrequency, getListInt(TX_ROUTE))) {
                    getUnreachParamExceptionManager().addParam(TX_LENGTH.name(), txLength, pulseTX.getPulseDuration(), ((NumberParam) getParam(TX_LENGTH)).getMaxValue(), "Pulse length too short to reach RF power with this pulse shape");
                    txLength = pulseTX.getPulseDuration();
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
        set(Tx_blanking, tx_amp != 0 );

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

        txLengthMax = txLength;
        if ( powerInput == PowerInput.FA) {
            pulseTX.setAmp(tx_amp); // for nutation and automatic setting using FA, amplitude is always variable
        }

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
        if (isGradClocked){blanking_time = ceilToGradClock(blanking_time, gradClockNumber);}
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
        gMaxSeq = Math.abs(gradSlice.getAmplitude()) > gMaxSeq ? Math.abs(gradSlice.getAmplitude()): gMaxSeq;

        // ---------------------------------------------------------------------
        // calculate calibration gradient
        // ---------------------------------------------------------------------

        ShapeGradient shapeGradient = ShapeGradient.createShapeGradient(getSequence(), calibShape, isGradClocked, Grad_shape_amp_1, Grad_shape_amp_2, Grad_shape_amp_3, Grad_shape_1,
                Grad_shape_2, Grad_shape_3, Time_shapegrad_1, Time_shapegrad_2, Time_shapegrad_3);
        double calibGradAmp = getDouble(CALIB_GRAD_AMP);
        double gradLength1 = getDouble(CALIB_GRAD_LENGTH_1);
        double gradLength2 = getDouble(CALIB_GRAD_LENGTH_2);
        double gradLength3 = getDouble(CALIB_GRAD_LENGTH_3);
        double chirpStart = getDouble(CALIB_GRAD_CHIRP_START);
        double chirpEnd = getDouble(CALIB_GRAD_CHIRP_STOP);
        int nbPointsGrad = getInt(CALIB_GRAD_NB_POINT);
        //double
        if (calibShape.equalsIgnoreCase("sinc")){
            shapeGradient.initSinc(calibGradAmp, nbPointsGrad, gradLength1);
        } else if (calibShape.equalsIgnoreCase("gaussian")) {
            shapeGradient.initGauss(calibGradAmp, nbPointsGrad, gradLength1);
        } else if (calibShape.equalsIgnoreCase("chirp")) {
            shapeGradient.initChirp(calibGradAmp,nbPointsGrad, gradLength1, chirpStart, chirpEnd);
            System.out.println("gradlength = " + gradLength1);
        } else if (calibShape.equalsIgnoreCase("trapezoid")) {
            shapeGradient.initTrapezoid(calibGradAmp, nbPointsGrad, gradLength1, gradLength2, gradLength3);
        } else if (calibShape.equalsIgnoreCase("triangle")) {
            shapeGradient.initTriangle(calibGradAmp, nbPointsGrad, gradLength1, gradLength2);
        } else if (calibShape.equalsIgnoreCase("None")){
            shapeGradient.setNone();
        }
        shapeGradient.safetyCheck(getDouble(CALIB_GRAD_SLEW_RATE_FACTOR));
        getParam(SLEW_RATE_MAX_SHAPE).setValue(ceilToSubDecimal(shapeGradient.getMaxSlewRateShape(), 3));
        getParam(SLEW_RATE_MAX_SYSTEM).setValue(ceilToSubDecimal(shapeGradient.getMaxSlewRateSystem(), 3));
        if (nb_scan_2d == 1) {
            shapeGradient.setAmplitudeTable();
        } else if (nb_scan_2d == 2) {
            shapeGradient.setAmplitudeTable2();
        } else{
            shapeGradient.setAmplitudeTable3();
        }
        if (!calibShape.equalsIgnoreCase("None")) {
            getParam(CALIB_GRAD_NB_POINT).setValue(shapeGradient.getNbPoints());
        }
        getParam(CALIB_GRAD_LENGTH_EFF_1).setValue(shapeGradient.getGradLength1());
        getParam(CALIB_GRAD_LENGTH_EFF_2).setValue(shapeGradient.getGradLength2());
        getParam(CALIB_GRAD_LENGTH_EFF_3).setValue(shapeGradient.getGradLength3());
        gMaxSeq = calibGradAmp > gMaxSeq ? calibGradAmp : gMaxSeq;

        // calculate SLICE_refocusing
        double grad_ref_application_time = isGradClocked? ceilToGradClock(getDouble(GRADIENT_REFOC_TIME), gradClockNumber):getDouble(GRADIENT_REFOC_TIME);
        setSequenceTableSingleValue(Time_grad_ref, isMultiplanar ? grad_ref_application_time : minInstructionDelay);
        Gradient gradSliceRefPhase3D = Gradient.createGradient(getSequence(), Grad_amp_slice_ref, Time_grad_ref, Grad_shape_up, Grad_shape_down, Time_grad_ramp, nucleus);
        if (isMultiplanar) {
            gradSliceRefPhase3D.refocalizeGradient(gradSlice, 0.5);
        }
        gradSliceRefPhase3D.applyAmplitude();
        gMaxSeq = Math.abs(gradSliceRefPhase3D.getAmplitude()) > gMaxSeq ? Math.abs(gradSliceRefPhase3D.getAmplitude()) : gMaxSeq;

        // -----------------------------------------------
        // calculate ADC observation time
        // -----------------------------------------------
        double preDelay = getDouble(CALIB_DELAY_BEFORE_GRAD);
        if (isGradClocked) {preDelay = ceilToGradClock(preDelay, gradClockNumber);}
        setSequenceTableSingleValue(Time_rx, preDelay);
        double timeRxContinue = observation_time - preDelay - shapeGradient.getGradObjectLength();
        if (isGradClocked){timeRxContinue = ceilToGradClock(timeRxContinue, gradClockNumber);}
        setSequenceTableSingleValue(Time_rx_continue, timeRxContinue);
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
        if (is_spoiler)
            gradSpoiler.addSpoiler(grad_spoiler_amp);
        gradSpoiler.applyAmplitude();

        if (is_spoiler)
            gMaxSeq = Math.abs(grad_spoiler_amp) > gMaxSeq ? Math.abs(grad_spoiler_amp) : gMaxSeq;
        getParam(GRAD_MAX_SEQUENCE).setValue(gMaxSeq);

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

        Table time_te_delay_table = setSequenceTableSingleValue(Time_te_delay);

        boolean isDelayEcho = (getBoolean(DELAY_ECHO));
        double delay1 = Math.max(minInstructionDelay, getDouble(DELAY_ECHO_TIME));
        if (isDelayEcho) {
            te = delay1 + time1;
            getParam(ECHO_TIME).setValue(te);
        } else {
            if (te < te_min) {
                te_min = ceilToSubDecimal(te_min, 5);
                getUnreachParamExceptionManager().addParam(ECHO_TIME.name(), te, te_min, ((NumberParam) getParam(ECHO_TIME)).getMaxValue(), "Tx-Rx Time too short for the User Mx1D and SW");
                te = te_min;//
            }
        }
        delay1 = isGradClocked? ceilToGradClock(te-time1, gradClockNumber) : te - time1;
        time_te_delay_table.add(delay1);
        getParam(DELAY_ECHO_TIME).setValue(delay1);
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
        getSequenceParam(Synchro_Trigger).setValue(isTrigger ? TimeElement.Trigger.External : TimeElement.Trigger.Timer);
        getSequenceParam(Synchro_Trigger).setLocked(true);

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
        Table time_last_delay_table = setSequenceTableSingleValue(Time_last_delay);

        double last_delay = tr - time_seq_to_end_spoiler;
        if (isGradClocked) {
            last_delay = ceilToGradClock(last_delay, gradClockNumber);
            tr = time_seq_to_end_spoiler + last_delay;
            getParam(REPETITION_TIME).setValue(tr);
        }
        time_last_delay_table.add(last_delay);


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

        List<Double> off_center_distance_3D_list = getListDouble(CALIB_LOCATIONS);
        int nbLocations = off_center_distance_3D_list.size();
        if (nbLocations > 0){
            double [] frequency_center_3D_90_array = new double[nbLocations];
            nb_scan_3d = nbLocations;
            set(Nb_3d, nb_scan_3d);
            getParam(ACQUISITION_MATRIX_DIMENSION_3D).setValue(nb_scan_3d);
            for (int i = 0; i < off_center_distance_3D_list.size(); i++) {
                frequency_center_3D_90_array[i] = -grad_amp_slice_mTpm * off_center_distance_3D_list.get(i) * (GradientMath.GAMMA);
            }
            setSequenceTableValues(Tx_freq_offset, Order.Three, frequency_center_3D_90_array);
        } else {
            setSequenceTableSingleValue(Tx_freq_offset, frequency_center_3D_90);
        }

        // calculate total sequence time
        double total_acquisition_time = tr * nb_scan_4d * nb_scan_3d * nb_scan_2d * nb_scan_1d;
        getParam(SEQUENCE_TIME).setValue(total_acquisition_time);

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
    private double ceilToGradClock(double numberToBeRounded, int... factor) {
        double freq = gradFreq;
        if (factor.length == 1) {
            freq /= (double) factor[0];
        }
        return Math.ceil(numberToBeRounded * freq) / freq;
    }
    private double roundToGradClock(double numberToBeRounded, int... factor) {
        double freq = gradFreq;
        if (factor.length == 1) {
            freq /= (double) factor[0];
        }
        return Math.max(1, Math.round(numberToBeRounded * freq)) / freq;
    }

    private double roundSWToGradClock(double sw, int acq1D) {
        //get the ADC timing as multiple of gradient clock with allowed SW
        // depending if the number of points has common divider with the gradient clock the sw steps may be different
        double acqTime = (double) acq1D / sw;
        double clock_11Grad = Math.round(gradFreq11 * acqTime);
        double common_divider = acq1D / ((double) gcd(acq1D, 64 * 7 * 5));
        clock_11Grad = Math.round(clock_11Grad / (common_divider)) * common_divider;
        acqTime = clock_11Grad / gradFreq11;
        return acq1D / acqTime;
    }

    private double floorSWToGradClock(double sw, int acq1D) {
        double acqTime = (double) acq1D / sw;
        double clock_11Grad = (gradFreq11 * acqTime);
        double common_divider = acq1D / ((double) gcd(acq1D, 64 * 7 * 5));
        clock_11Grad = Math.ceil(clock_11Grad / (common_divider)) * common_divider;
        acqTime = clock_11Grad / gradFreq11;
        return acq1D / acqTime;
    }

    private static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    //<editor-fold defaultstate="collapsed" desc="Generated Code (RS2D)">
    protected void addUserParams() {
        addMissingUserParams(U.values());
    }

    public String getName() {
        return "OnePulse_Grad_Slc";
    }

    public String getVersion() {
        return "v1.1.1";
    }
    //</editor-fold>


}