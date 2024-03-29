package rs2d.sequence.common;

import rs2d.spinlab.data.transformPlugin.TransformPlugin;
import rs2d.spinlab.api.Hardware;
import rs2d.spinlab.instrument.util.GradientMath;
import rs2d.spinlab.sequence.Sequence;
import rs2d.spinlab.sequence.table.Shape;
import rs2d.spinlab.sequence.table.Table;
import rs2d.spinlab.sequence.table.Utility;
import rs2d.spinlab.sequenceGenerator.GeneratorSequenceParamEnum;
import rs2d.spinlab.tools.table.Order;
import rs2d.spinlab.tools.utility.Nucleus;

/**
 * Class Gradient: small Common Version 1.4S
 */
public class Gradient {
    protected Table amplitudeTable;
    protected Table flatTimeTable;
    protected Shape shapeUpTable;
    protected Shape shapeDownTable;
    protected Table rampTimeUpTable;
    protected Table rampTimeDownTable;

    protected double amplitude = Double.NaN;
    protected double staticArea = Double.NaN;

    protected double[] amplitudeArray = null;
    protected double maxAreaPE = Double.NaN;

    protected int steps = -1;
    protected Order order = Order.FourLoop;

    protected double grad_shape_rise_time = Double.NaN;
    protected double equivalentTime = Double.NaN;

    // Slc
    protected double sliceThicknessExcitation = Double.NaN;
    protected double txBandwidth = Double.NaN;

    // RO
    protected double spectralWidth = Double.NaN;

    // PE
    protected boolean bPhaseEncoding = false;
    protected boolean bStaticGradient = false;
    protected boolean isKSCentred = false;
    protected double fovPhase = Double.NaN;
    protected double k0pos = Double.NaN;

    protected double spoilerExcess = Double.NaN;
    protected double minTopTime = Double.NaN;
    protected Gradient gradFlowComp = null;

    protected static double gMax = Math.abs(GradientMath.getMaxGradientStrength());
    Nucleus nucleus = Nucleus.H1;

    public Gradient(Table amplitudeTab, Table flat_TimeTab, Shape shapeUpTab, Shape shapeDownTab, Table rampTimeUpTab, Table rampTimeDownTab) {
        amplitudeTable = amplitudeTab;
        flatTimeTable = flat_TimeTab;
        shapeUpTable = shapeUpTab;
        shapeDownTable = shapeDownTab;
        rampTimeUpTable = rampTimeUpTab;
        rampTimeDownTable = rampTimeDownTab;
        gMax = Math.abs(GradientMath.getMaxGradientStrength());
        init();
    }

    public Gradient(Table amplitudeTab, Table flat_TimeTab, Shape shapeUpTab, Shape shapeDownTab, Table rampTimeUpTab, Table rampTimeDownTab, Nucleus nucleus) {
        amplitudeTable = amplitudeTab;
        flatTimeTable = flat_TimeTab;
        shapeUpTable = shapeUpTab;
        shapeDownTable = shapeDownTab;
        rampTimeUpTable = rampTimeUpTab;
        rampTimeDownTable = rampTimeDownTab;
        gMax = Math.abs(GradientMath.getMaxGradientStrength());
        this.nucleus = nucleus;
        init();
    }

    public static Gradient createGradient(Sequence sequence, GeneratorSequenceParamEnum amplitudeTab, GeneratorSequenceParamEnum flat_TimeTab, GeneratorSequenceParamEnum shapeUpTab, GeneratorSequenceParamEnum shapeDownTab, GeneratorSequenceParamEnum rampTimeTab) {
        return new Gradient(sequence.getPublicTable(amplitudeTab.name()), sequence.getTable(flat_TimeTab.name()), (Shape) sequence.getTable(shapeUpTab.name()),
                (Shape) sequence.getPublicTable(shapeDownTab.name()), sequence.getTable(rampTimeTab.name()), sequence.getTable(rampTimeTab.name()));
    }

    public static Gradient createGradient(Sequence sequence, GeneratorSequenceParamEnum amplitudeTab, GeneratorSequenceParamEnum flat_TimeTab, GeneratorSequenceParamEnum shapeUpTab, GeneratorSequenceParamEnum shapeDownTab, GeneratorSequenceParamEnum rampTimeUpTab, GeneratorSequenceParamEnum rampTimeDownTab) {
        return new Gradient(sequence.getPublicTable(amplitudeTab.name()), sequence.getTable(flat_TimeTab.name()), (Shape) sequence.getTable(shapeUpTab.name()),
                (Shape) sequence.getPublicTable(shapeDownTab.name()), sequence.getTable(rampTimeUpTab.name()), sequence.getTable(rampTimeDownTab.name()));
    }

    //non proton
    public static Gradient createGradient(Sequence sequence, GeneratorSequenceParamEnum amplitudeTab, GeneratorSequenceParamEnum flat_TimeTab, GeneratorSequenceParamEnum shapeUpTab, GeneratorSequenceParamEnum shapeDownTab, GeneratorSequenceParamEnum rampTimeTab, Nucleus nucleus) {
        return new Gradient(sequence.getPublicTable(amplitudeTab.name()), sequence.getTable(flat_TimeTab.name()), (Shape) sequence.getTable(shapeUpTab.name()),
                (Shape) sequence.getPublicTable(shapeDownTab.name()), sequence.getTable(rampTimeTab.name()), sequence.getTable(rampTimeTab.name()), nucleus);
    }

    public static Gradient createGradient(Sequence sequence, GeneratorSequenceParamEnum amplitudeTab, GeneratorSequenceParamEnum flat_TimeTab, GeneratorSequenceParamEnum shapeUpTab, GeneratorSequenceParamEnum shapeDownTab, GeneratorSequenceParamEnum rampTimeUpTab, GeneratorSequenceParamEnum rampTimeDownTab, Nucleus nucleus) {
        return new Gradient(sequence.getPublicTable(amplitudeTab.name()), sequence.getTable(flat_TimeTab.name()), (Shape) sequence.getTable(shapeUpTab.name()),
                (Shape) sequence.getPublicTable(shapeDownTab.name()), sequence.getTable(rampTimeUpTab.name()), sequence.getTable(rampTimeDownTab.name()), nucleus);
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                  general  methodes
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    protected void init() {
        computeShapeRiseTime();
        prepareEquivalentTime();
    }

    public double getStaticArea() {
        if (Double.isNaN(staticArea)) {
            return 0.0;
        } else {
            return staticArea;
        }
    }

    public void setStaticArea(double staticArea) {
        this.staticArea = staticArea;
    }

    public double getAmplitude() {
        if (Double.isNaN(amplitude)) {
            return 0.0;
        } else {
            return amplitude;
        }
    }

    public Table getFlatTimeTable() {
        return flatTimeTable;
    }

    public Shape getShapeUpTable() {
        return shapeUpTable;
    }

    public Shape getShapeDownTable() {
        return shapeDownTable;
    }

    public Table getRampTimeUpTable() {
        return rampTimeUpTable;
    }

    public Table getRampTimeDownTable() {
        return rampTimeDownTable;
    }

    public double getAmplitude_mTpm() {
        if (Double.isNaN(amplitude)) {
            return 0.0;
        } else {
            double amp = amplitude;
            if (amplitude == 0.0 & amplitudeArray != null) {
                amp = amplitudeArray[0];
            }
            return amp * gMax / 100.0;
        }
    }

    public double getShapeRiseTime() {
        return grad_shape_rise_time;
    }

    public double getAmplitudeArray(int pos) {
        if (amplitudeArray == null || pos >= steps) {
            return Double.NaN;
        } else {
            return amplitudeArray[pos];
        }
    }

    public double getAmplitudeArray_mTpm(int pos) {
        if (amplitudeArray == null || pos >= steps) {
            return Double.NaN;
        } else {
            return amplitudeArray[pos] * gMax / 100.0;
        }
    }

    public double getSliceThickness() {
        return sliceThicknessExcitation;
    }

    public double getSpoilerExcess() {
        return spoilerExcess;
    }

    public double getMinTopTime() {
        return minTopTime;
    }

    public int getSteps() {
        return steps;
    }

    public Order getOrder() {
        return order;
    }

    public double getK0pos() {
        return k0pos;
    }

    public void setK0pos(double kspos0) {
        k0pos = kspos0;
    }

    public double getMaxArea_PE() {
        return maxAreaPE;
    }

    public void setMaxArea_PE(double maxArea_PE) {
        maxAreaPE = maxArea_PE;
    }


    public double getEquivalentTime() {
        return equivalentTime;
    }

    public double getGradShapeRiseTime() {
        computeShapeRiseTime();
        return equivalentTime;
    }

    public double getTotalArea() {
        double totalArea = 0.0;
        if (!Double.isNaN(maxAreaPE)) {
            totalArea += maxAreaPE;
        }
        if (!Double.isNaN(staticArea)) {
            totalArea += staticArea;
        }
        return totalArea;
    }

    public double getTotalAbsArea() {
        return Math.abs(getTotalArea());
    }

    public void calculateStaticArea() {
        if (Double.isNaN(equivalentTime)) {
            prepareEquivalentTime();
        }
        staticArea = (equivalentTime) * amplitude;
    }

    /**
     * calculate Static Amplitude from staticArea
     */
    public void calculateStaticAmplitude() {
        if (Double.isNaN(equivalentTime)) {
            prepareEquivalentTime();
        }
        amplitude = staticArea / (equivalentTime);
    }

    public void setAmplitude(double... values) {
        if (values.length == 1) {
            amplitude = values[0];
        } else {
            amplitudeArray = new double[values.length];
            int i = 0;
            for (double value : values) {
                //System.out.println(i + " " + value);
                amplitudeArray[i] = value;
                i += 1;
            }
            steps = i;
        }
    }

    public void setAmplitude(Order order, double... values) {
        setAmplitude(values);
        applyAmplitude(order);
    }


    /**
     * write the prepared amplitude into the Sequence_parameter
     */
    public void applyAmplitude() {
        double offset = 0.0;
        if (!Double.isNaN(amplitude)) {
            offset = amplitude;
        }
        if (amplitudeArray != null) {
            setSequenceTableValues(amplitudeTable, order);
            for (int i = 0; i < steps; i++) {
                amplitudeTable.add(amplitudeArray[i] + offset);
            }
        } else {
            order = Order.FourLoop;
            setSequenceTableValues(amplitudeTable, order);
            amplitudeTable.add(offset);
        }
    }

    public void applyAmplitude(Order taborder) {
        order = taborder;
        applyAmplitude();
    }

    /**
     * Check if the statid and dynamic gradient do not exceed the gradient max:
     */
    public double[] checkGradientMax() {
        double gradMax = 0;
        double gradMin = 0;
        double offset = 0.0;
        if (!Double.isNaN(amplitude)) {
            offset = amplitude;
        }
        if (amplitudeArray != null) {
            for (int i = 0; i < steps; i++) {
                gradMax = Math.max(gradMax, (amplitudeArray[i] + offset));
                gradMin = Math.min(gradMin, (amplitudeArray[i] + offset));
            }
        } else {
            gradMax = Math.max(gradMax, Math.abs(+offset));
        }
        if ((gradMax > 100.0) || (gradMin < -100.0)) {
            System.out.println("Warning - Gradient Min and  Max: " + gradMax + " " + gradMin);
        }
        double[] gradMaxMin = new double[2];
        gradMaxMin[0] = gradMax;
        gradMaxMin[1] = gradMin;
        return (gradMaxMin);
    }

    /**
     * Calculate equivalent shape rise time from rampTime ans shapes
     * equivalent time for rectangular gradient with same Area and amplitude
     */
    protected void computeShapeRiseTime() {
        double grad_shape_rise_factor_up = Utility.voltageFillingFactor(shapeUpTable);
        double grad_shape_rise_factor_down = Utility.voltageFillingFactor(shapeDownTable);
        double grad_up_rise_time = rampTimeUpTable.get(0).doubleValue();
        double grad_down_rise_time = rampTimeDownTable.get(0).doubleValue();
        grad_shape_rise_time = grad_shape_rise_factor_up * grad_up_rise_time + grad_shape_rise_factor_down * grad_down_rise_time;
    }

    /**
     * Calculate equivalentTime of a rectangular gradient with same Area and amplitude
     */
    public void prepareEquivalentTime() {
        if (Double.isNaN(grad_shape_rise_time)) {
            computeShapeRiseTime();
        }
        minTopTime = flatTimeTable.get(0).doubleValue();
        equivalentTime = (flatTimeTable.get(0).doubleValue() + grad_shape_rise_time);
    }

    public void refocalizeGradient() {
        calculateStaticAmplitude();
    }

    /**
     * Refocalize the gradient with ratio of the top time
     *
     * @param gradToRef : Reference gradient to refocalize
     * @param ratio     : ratio of the top time that will be taken into account
     * @param blockID   : ramp up ID = 1 or ramp down ID = 3
     */

    public void refocalizeGradient_firstValue(Gradient gradToRef, double ratio, int blockID) {
        bStaticGradient = true;
        // Compute the equivalent time with respect to a rectangular gradient that is used later to compute the area to compensate
        double gradToRefTime = (gradToRef.getEquivalentTimeBlock(blockID)[0] + gradToRef.getEquivalentTimeFlat(gradToRef.flatTimeTable, Math.abs(ratio))[0]);
        if (Double.isNaN(equivalentTime)) {
            prepareEquivalentTime();
        }

        if (gradToRef.getSteps() > 1) {
            amplitudeArray = new double[1];
            for (int i = 0; i < 1; i++) {
                amplitudeArray[i] = -(ratio > 0 ? 1 : -1) * (gradToRef.getAmplitudeArray(i) * gradToRefTime) / (equivalentTime);
//                    amplitudeArray[i] = -gradToRef.getAmplitudeArray(i);
            }
            System.out.println(amplitudeArray[0]);
            steps = 1;
        } else {
            double amp = !Double.isNaN(gradToRef.getAmplitude()) ? gradToRef.getAmplitude() : gradToRef.getAmplitudeArray(0);
            double gradArea = (ratio > 0 ? 1 : -1) * gradToRefTime * amp;
            staticArea = -gradArea;
            calculateStaticAmplitude();
        }
    }

    //    refocalize the entire gradient
    public void refocalizeTotalGradient(Gradient gradToRef) {
        bStaticGradient = true;
        double gradToRefTime = (gradToRef.getEquivalentTimeBlock(1)[0] + gradToRef.getEquivalentTimeFlat(gradToRef.flatTimeTable, 1)[0] + gradToRef.getEquivalentTimeBlock(3)[0]);
        if (Double.isNaN(equivalentTime)) {
            prepareEquivalentTime();
        }

        if (gradToRef.getSteps() > 1) {
            amplitudeArray = new double[gradToRef.getSteps()];
            for (int i = 0; i < gradToRef.getSteps(); i++) {
                amplitudeArray[i] = (gradToRef.getAmplitudeArray(i) * gradToRefTime) / (equivalentTime);
//                    amplitudeArray[i] = -gradToRef.getAmplitudeArray(i);
            }
            steps = gradToRef.getSteps();
        } else {
            double amp = !Double.isNaN(gradToRef.getAmplitude()) ? gradToRef.getAmplitude() : gradToRef.getAmplitudeArray(0);
            double gradArea = gradToRefTime * amp;
            staticArea = -gradArea;
            calculateStaticAmplitude();
        }
    }

    //    refocalize the gradient with ratio of the top
    public void refocalizeGradient(Gradient gradToRef, double ratio) {
        bStaticGradient = true;
        double gradToRefTime = (gradToRef.getEquivalentTimeBlock(3)[0] + gradToRef.getEquivalentTimeFlat(gradToRef.flatTimeTable, Math.abs(ratio))[0]);
        if (Double.isNaN(equivalentTime)) {
            prepareEquivalentTime();
        }

        if (gradToRef.getSteps() > 1) {
            amplitudeArray = new double[gradToRef.getSteps()];
            for (int i = 0; i < gradToRef.getSteps(); i++) {
                amplitudeArray[i] = (ratio > 0 ? 1 : -1) * (gradToRef.getAmplitudeArray(i) * gradToRefTime) / (equivalentTime);
//                    amplitudeArray[i] = -gradToRef.getAmplitudeArray(i);
            }
            steps = gradToRef.getSteps();
        } else {
            double amp = !Double.isNaN(gradToRef.getAmplitude()) ? gradToRef.getAmplitude() : gradToRef.getAmplitudeArray(0);
            double gradArea = (ratio > 0 ? 1 : -1) * gradToRefTime * amp;
            staticArea = -gradArea;
            calculateStaticAmplitude();
        }
    }

    public void refocalizeGradientWithFlowComp(Gradient grad, double ratioTime, Gradient gradflowcomp) {
        gradFlowComp = gradflowcomp;
        // from Gs to middle of G_refocus
        double Gs_Arm = grad.getRampTimeUpTable().get(0).doubleValue() + grad.getFlatTimeTable().get(0).doubleValue() + grad.getRampTimeDownTable().get(0).doubleValue()
                + getRampTimeUpTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() / 2
                - grad.getMomentArmEnd(ratioTime)[0];
        double Gs_A = (grad.getEquivalentTimeBlock(3)[0] + grad.getEquivalentTimeFlat(grad.flatTimeTable, ratioTime)[0]) * grad.getAmplitude();
        double Gs_M = Gs_Arm * Gs_A;

        double G3_Arm_toStart = getFlatTimeTable().get(0).doubleValue() / 2 + getRampTimeDownTable().get(0).doubleValue();
        gradflowcomp.flowCalculateGradientAmpRefocMomentum(+Gs_M, G3_Arm_toStart);
        bStaticGradient = true;
        staticArea = -(Gs_A + gradflowcomp.getStaticArea());

        calculateStaticAmplitude();
    }

    public void refocalizeGradientWithFlowComp(Gradient grad, double ratioTime, Gradient gradflowcomp, double ratioMomentum) {
        gradFlowComp = gradflowcomp;
        // from Gs to middle of G_refocus
        double Gs_Arm = grad.getRampTimeUpTable().get(0).doubleValue() + grad.getFlatTimeTable().get(0).doubleValue() + grad.getRampTimeDownTable().get(0).doubleValue()
                + getRampTimeUpTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() / 2
                - grad.getMomentArmEnd(ratioTime)[0];
        double Gs_A = (grad.getEquivalentTimeBlock(3)[0] + grad.getEquivalentTimeFlat(grad.flatTimeTable, ratioTime)[0]) * grad.getAmplitude();
        double Gs_M = Gs_Arm * Gs_A;

        double G3_Arm_toStart = getFlatTimeTable().get(0).doubleValue() / 2 + getRampTimeDownTable().get(0).doubleValue();
        gradflowcomp.flowCalculateGradientAmpRefocMomentum(Gs_M * ratioMomentum, G3_Arm_toStart);
        bStaticGradient = true;
        staticArea = -(Gs_A + gradflowcomp.getStaticArea());

        calculateStaticAmplitude();
    }

    public void refocalizeGradientWithFlowCompWithDelay(Gradient grad, double ratioTime, Gradient gradflowcomp, double delta_G2_Grad) {
        gradFlowComp = gradflowcomp;
        // from Gr to middle of G_refocus
        double Gs_Arm = getRampTimeDownTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() / 2
                + delta_G2_Grad
                + grad.getMomentArmStart(ratioTime)[0];


        double Gs_A = (grad.getEquivalentTimeBlock(1)[0] + grad.getEquivalentTimeFlat(grad.flatTimeTable, ratioTime)[0]) * grad.getAmplitude();
        double Gs_M = Gs_Arm * Gs_A;
        double G2_Arm = gradflowcomp.getFlatTimeTable().get(0).doubleValue() / 2 + gradflowcomp.getRampTimeUpTable().get(0).doubleValue();
        flowCalculateGradientAmpRefocMomentum(+Gs_M, G2_Arm);

        bStaticGradient = true;
        gradflowcomp.setStaticArea(-(Gs_A + getStaticArea()));
        gradflowcomp.calculateStaticAmplitude();

    }

    public void refocalizeGradientWithFlowCompWithDelay(Gradient grad, double ratioTime, Gradient gradflowcomp, double delta_G2_Grad, double ratioMomentum) {
        gradFlowComp = gradflowcomp;
        // from Gr to middle of G_refocus
        double Gs_Arm = getRampTimeDownTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() / 2
                + delta_G2_Grad
                + grad.getMomentArmStart(ratioTime)[0];


        double Gs_A = (grad.getEquivalentTimeBlock(1)[0] + grad.getEquivalentTimeFlat(grad.flatTimeTable, ratioTime)[0]) * grad.getAmplitude();
        double Gs_M = Gs_Arm * Gs_A;
        double G2_Arm = gradflowcomp.getFlatTimeTable().get(0).doubleValue() / 2 + gradflowcomp.getRampTimeUpTable().get(0).doubleValue();
        flowCalculateGradientAmpRefocMomentum(Gs_M * ratioMomentum, G2_Arm);

        bStaticGradient = true;
        gradflowcomp.setStaticArea(-(Gs_A + getStaticArea()));
        gradflowcomp.calculateStaticAmplitude();


    }

    public void flowCalculateGradientAmpRefocMomentum(double Momentum, double timeOffset) {
        bStaticGradient = true;
        amplitude = Momentum / ((flatTimeTable.get(0).doubleValue() + rampTimeUpTable.get(0).doubleValue())
                * (flatTimeTable.get(0).doubleValue() / 2 + rampTimeUpTable.get(0).doubleValue() + timeOffset));
        calculateStaticArea();
    }

    public boolean refocalizeGradientWithAmplitude(Gradient grad, double ratio, double amplitude) {
        if (Double.isNaN(grad_shape_rise_time)) {
            computeShapeRiseTime();
        }
        staticArea = -grad.getStaticArea() * ratio;
        boolean test_Amplitude = true;
        equivalentTime = staticArea / amplitude;
        double topTime = equivalentTime - grad_shape_rise_time;
        if (topTime < 0.000004) {
            topTime = 0.000004;
            test_Amplitude = false;
        }
        flatTimeTable.set(0, topTime);
        prepareEquivalentTime();
        calculateStaticAmplitude();
        return test_Amplitude;
    }

    public void rePrepare() {
        prepareEquivalentTime();
        if (bPhaseEncoding)
            preparePhaseEncoding();
        if (bStaticGradient)
            calculateStaticAmplitude();

    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     RO             Readout  methodes             RO
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    /**
     * calculate readout Gradient Amplitude
     *
     * @param spectralWidth : recepion bandwith
     * @param fov           : field of view
     * @return testSpectralWidth : false if the Spectralwidth need to be nicreased(call getSpectralWidth() )
     */
    public boolean calculateReadoutGradient(double spectralWidth, double fov) throws Exception {
        boolean testSpectralWidth = true;
        this.spectralWidth = spectralWidth;
        amplitude = spectralWidth / ((GradientMath.GAMMA / nucleus.getRatio()) * fov) * 100.0 / gMax;                 // amplitude in T/m
        if (Math.abs(amplitude) > 100.0) {
            this.spectralWidth = solveSpectralWidthMax(fov);
            amplitude = this.spectralWidth / ((GradientMath.GAMMA / nucleus.getRatio()) * fov) * 100.0 / gMax;                 // amplitude in T/m
            testSpectralWidth = false;
        }
        calculateStaticArea();
        return testSpectralWidth;
    }

    /**
     * calculate spectral width max for a given FOV
     *
     * @param fov : field of view
     * @return sw : nearest bandwidth possible
     */
    public double solveSpectralWidthMax(double fov) {
        return Hardware.getFloorSpectralWidth(gMax * GradientMath.GAMMA * fov);
    }

    public double getSpectralWidth() {
        return spectralWidth;
    }

    /**
     * set READOUT gradient Amplitude ETL values with back and forth(+/-) sign
     *
     * @param ETL        : number of gradient values
     * @param tableorder : acquisition order
     */
    public void applyReadoutEchoPlanarAmplitude(int ETL, Order tableorder) {
        order = tableorder;
        if (!Double.isNaN(amplitude) && ETL > 1) {
            steps = ETL;
            amplitudeArray = new double[steps];
            for (int i = 0; i < steps; i++) {
                if (i % 2 == 0) {
                    amplitudeArray[i] = amplitude;
                } else {
                    amplitudeArray[i] = -amplitude;
                }
            }
            amplitude = 0.0;
        }
        applyAmplitude();
    }

    /**
     * calculate READOUT refocusing gradient Amplitude handeling ETL
     *
     * @param grad : Readout Gradient
     */
    public void refocalizeReadoutGradient(Gradient grad, double ratio) {
        int rOSteps = grad.getSteps();
        if (rOSteps > 0) {
            ratio = (rOSteps % 2) == 1 ? ratio : 1 - ratio;
            staticArea = -grad.getAmplitudeArray(rOSteps - 1) * grad.getEquivalentTime() * ratio;
        } else {
            staticArea = -grad.getAmplitude() * grad.getEquivalentTime() * ratio;
        }
        calculateStaticAmplitude();
    }

    /*
     * calculate READOUT refocusing
     *
     * @param grad : Readout Gradient
     * @param ratio : ratio to compensate
     */
    public void refocalizeReadoutGradients(Gradient grad, double ratio) {
        steps = grad.getSteps();
        amplitudeArray = new double[steps];
        for (int i = 0; i < steps; i++) {
            // flatTimeTable.get(0).doubleValue() + grad_shape_rise_time
            amplitudeArray[i] = -grad.getAmplitudeArray(i) * grad.getEquivalentTime() / this.getEquivalentTime() * ratio;
        }

        order = grad.getOrder();
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     Slc             Slice Selection             Slc
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public boolean prepareSliceSelection(double tx_bandwidth, double slice_thickness_excitation) {
        boolean testSliceThickness = true;
        txBandwidth = tx_bandwidth;
        this.sliceThicknessExcitation = slice_thickness_excitation;
        amplitude = (tx_bandwidth / ((GradientMath.GAMMA / nucleus.getRatio()) * sliceThicknessExcitation)) * 100.0 / gMax;                 // amplitude in T/m
        if (Math.abs(amplitude) > 100.0) {
            sliceThicknessExcitation = ceilToSubDecimal(tx_bandwidth / ((GradientMath.GAMMA / nucleus.getRatio()) * gMax), 6);
            amplitude = (tx_bandwidth / ((GradientMath.GAMMA / nucleus.getRatio()) * sliceThicknessExcitation)) * 100.0 / gMax;                 // amplitude in T/m
            testSliceThickness = false;
        }
        calculateStaticArea();
        return testSliceThickness;
    }

    public boolean prepareSliceSelection(double tx_bandwidth, double slice_thickness_excitation, boolean Negative) {
        boolean testSliceThickness = prepareSliceSelection(tx_bandwidth, slice_thickness_excitation);
        amplitude *= Negative ? -1 : 1;
        calculateStaticArea();
        return testSliceThickness;
    }

    public void applyAmplitude(double sliceThickness) {
        prepareSliceSelection(txBandwidth, sliceThickness);
        applyAmplitude();
    }


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //     PE             Phase Encoding             PE
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void preparePhaseEncoding() {
        preparePhaseEncoding(steps, fovPhase, isKSCentred);
    }

    public void preparePhaseEncoding(int matrixDimension, double fovDimPE, boolean isKSCentred) {
        bPhaseEncoding = true;
        steps = matrixDimension;
        fovPhase = fovDimPE;
        this.isKSCentred = isKSCentred;
        double grad_total_area_phase = prepPhaseGradTotalArea(steps, fovPhase);
        double grad_index_max_phase = prepPhaseGradIndexMax(this.isKSCentred);
        double grad_total_amp_phase = grad_total_area_phase / equivalentTime;
        amplitudeArray = new double[steps];
        for (int i = 0; i < steps; i++) {
            amplitudeArray[i] = -(grad_index_max_phase * grad_total_amp_phase) + i * grad_total_amp_phase / (steps - 1);
        }
    }

    public void preparePhaseEncoding(int matrixDimension, double fovDimPE, boolean isKSCentred, double ratioFlow) {
        bPhaseEncoding = true;
        steps = matrixDimension;
        fovPhase = fovDimPE;
        this.isKSCentred = isKSCentred;
        double grad_total_area_phase = prepPhaseGradTotalArea(steps, fovPhase) * ratioFlow;
        double grad_index_max_phase = prepPhaseGradIndexMax(this.isKSCentred);
        double grad_total_amp_phase = grad_total_area_phase / equivalentTime;
        amplitudeArray = new double[steps];
        for (int i = 0; i < steps; i++) {
            amplitudeArray[i] = -(grad_index_max_phase * grad_total_amp_phase) + i * grad_total_amp_phase / (steps - 1);
        }
    }

    public boolean prepareEPIBlip(int step, double fovDim) {
        staticArea = prepPhaseGradTotalArea(step, fovDim);
        amplitude = staticArea / equivalentTime;
        return amplitude <= 100.0;
    }

    public double getShapeFactor() {
        double grad_shape_rise_factor_up = Utility.voltageFillingFactor(shapeUpTable);
        double grad_shape_rise_factor_down = Utility.voltageFillingFactor(shapeDownTable);
        return (grad_shape_rise_factor_up + grad_shape_rise_factor_down) / 2;
    }

    public void preparePhaseEncodingForCheck(int matrixDimensionForCheck, int matrixDimension, double fovDim, boolean isKSCentred) {
        double grad_total_area_phase = prepPhaseGradTotalArea(matrixDimensionForCheck, fovDim);
        double grad_index_max_phase = prepPhaseGradIndexMax(isKSCentred);
        maxAreaPE = grad_index_max_phase * grad_total_area_phase;
        preparePhaseEncoding(matrixDimension, fovDim, isKSCentred);
    }


    // delta is the time from the end of the gradient to the echo
    public void preparePhaseEncodingForCheckWithFlowComp(int matrixDimensionForCheck, int matrixDimension, double fovDim, boolean isKSCentred, Gradient gradflowcomp, double delta) {
        gradFlowComp = gradflowcomp;

        double grad_total_area_phase = prepPhaseGradTotalArea(matrixDimensionForCheck, fovDim);
        double grad_index_max_phase = prepPhaseGradIndexMax(isKSCentred);
        gradflowcomp.setK0pos(getK0pos());

        double maxArea_PE = grad_index_max_phase * grad_total_area_phase;
        double W2 = gradflowcomp.getRampTimeUpTable().get(0).doubleValue() + gradflowcomp.getFlatTimeTable().get(0).doubleValue() + gradflowcomp.getRampTimeDownTable().get(0).doubleValue();
        double W1 = getRampTimeUpTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() + getRampTimeDownTable().get(0).doubleValue();
        double ratioA1 = -(W2 + 2 * delta) / (W1 + W2);
        double ratioA2 = (W1 + 2 * W2 + 2 * delta) / (W1 + W2);

        maxAreaPE = maxArea_PE * ratioA1;
        double maxAreaPE2 = maxArea_PE * ratioA2;
        gradflowcomp.setMaxArea_PE(maxAreaPE2);
        preparePhaseEncoding(matrixDimension, fovDim, isKSCentred, ratioA1);
        gradflowcomp.preparePhaseEncoding(matrixDimension, fovDim, isKSCentred, ratioA2);
    }

    public void preparePhaseEncodingForCheckWithFlowComp(int matrixDimensionForCheck, int matrixDimension, double fovDim, boolean isKSCentred, Gradient gradflowcomp, double delta, double ratioMomentum) {
        gradFlowComp = gradflowcomp;
        // to modify , flow Comp
        //to do: modify the calculation and prepare as well gradFlowComp Gradient

        double grad_total_area_phase = prepPhaseGradTotalArea(matrixDimensionForCheck, fovDim);
        double grad_index_max_phase = prepPhaseGradIndexMax(isKSCentred);
        gradflowcomp.setK0pos(getK0pos());

        double maxArea_PE = grad_index_max_phase * grad_total_area_phase;
        double W2 = gradflowcomp.getRampTimeUpTable().get(0).doubleValue() + gradflowcomp.getFlatTimeTable().get(0).doubleValue() + gradflowcomp.getRampTimeDownTable().get(0).doubleValue();
        double W1 = getRampTimeUpTable().get(0).doubleValue() + getFlatTimeTable().get(0).doubleValue() + getRampTimeDownTable().get(0).doubleValue();
        double k = ratioMomentum;
        double ratioA1 = -k * (W2 + 2 * delta) / (W1 + W2 * (2 - k) + 2 * delta * (1 - k));
//        double ratioA1 = -(W2 + 2 * delta) / (W1 + W2 );
//        double ratioA2 = (W1 + 2 * W2 + 2 * delta) / (W1 + W2);
        double ratioA2 = (W1 + 2 * W2 + 2 * delta) / (W1 + W2 * (2 - k) + delta * (1 - k));

        maxAreaPE = maxArea_PE * ratioA1;
        double maxAreaPE2 = maxArea_PE * ratioA2;
        gradflowcomp.setMaxArea_PE(maxAreaPE2);
        preparePhaseEncoding(matrixDimension, fovDim, isKSCentred, ratioA1);
        gradflowcomp.preparePhaseEncoding(matrixDimension, fovDim, isKSCentred, ratioA2);
    }

    public double prepPhaseGradTotalArea(int matrixDimension, double fovPhase) {
        return ((matrixDimension - 1) / ((GradientMath.GAMMA / nucleus.getRatio()) * fovPhase)) * 100.0 / gMax;
    }

    public double prepPhaseGradIndexMax(boolean isKSCentred) {
        double gradIndexMaxPhase;
        if (isKSCentred) {
            gradIndexMaxPhase = 1 / 2.0;// symetric k'space around zero
        } else {
            gradIndexMaxPhase = 1 / 2.0 + ((steps + 1) % 2) / (2.0 * ((float) steps - 1));// always go trough k0
        }
        k0pos = gradIndexMaxPhase * steps;
        return gradIndexMaxPhase;
    }


    public void refocalizePhaseEncodingGradient(Gradient grad) {
        steps = grad.getSteps();
        if (steps > 0) {
            order = grad.getOrder();
            amplitudeArray = new double[steps];
            for (int i = 0; i < steps; i++) {
                amplitudeArray[i] = -grad.getAmplitudeArray(i) * grad.getEquivalentTime() / equivalentTime;
            }
        }
    }

    // refolcalize two phase encoding gradient ( Flow comp)
    public double[] refocalizePhaseEncodingGradient(Gradient grad, Gradient grad2) {

        if (grad.getSteps() == grad2.getSteps()) {
            steps = grad.getSteps();
            if (steps > 0) {
                order = grad.getOrder();
                amplitudeArray = new double[steps];
                for (int i = 0; i < steps; i++) {
                    amplitudeArray[i] = -(grad.getAmplitudeArray(i) * grad.getEquivalentTime() + grad2.getAmplitudeArray(i) * grad2.getEquivalentTime()) / equivalentTime;
                }
            }
        }
        return amplitudeArray;
    }

    public double[] refocalizePhaseEncodingGradientSEEPI(Gradient grad, Gradient gradBlip, int echoTrainLength, boolean isPrephasingBefore180) {
        steps = grad.getSteps();
        if (steps > 0) {
            order = grad.getOrder();
            amplitudeArray = new double[steps];
            for (int i = 0; i < steps; i++) {
                amplitudeArray[i] = -((isPrephasingBefore180 ? -1 : 1) * grad.getAmplitudeArray(i) * grad.getEquivalentTime()
                        + gradBlip.getAmplitude() * gradBlip.getEquivalentTime() * echoTrainLength) / equivalentTime;
            }
        }
        return amplitudeArray;
    }


    public void reoderPhaseEncodingForSEEPI(int echoTrainLength) {
        int new_steps = steps / echoTrainLength;
        double[] newTable = new double[new_steps];
        int fact = 1;
        for (int i = 0; i < new_steps; i++) {
            int ii = i * fact;
            newTable[i] = -amplitudeArray[ii];
        }
        amplitudeArray = newTable;
        steps = new_steps;
    }

    // for internal dummy scans, add nbDummy scan before the scans
    public void reoderPhaseEncodingForSEEPIplusDummy(int nbDummy) {
        int new_steps = steps + nbDummy;
        double[] newTable = new double[new_steps];
        newTable[0] = 0;
        for (int i = 0; i < nbDummy; i++) {
            newTable[i] = 0;
        }
        for (int i = 0; i < steps; i++) {
            newTable[i + nbDummy] = -amplitudeArray[i];
        }
        amplitudeArray = newTable;
        steps = new_steps;

    }

    // for inline calibration add two scans without phase encoding before ansd after the PE
    public void reoderPhaseEncodingForSEEPIplus2() {
        int new_steps = steps + 2;
        double[] newTable = new double[new_steps];
        newTable[0] = 0;
        for (int i = 0; i < steps; i++) {
            newTable[i + 1] = -amplitudeArray[i];
        }
        newTable[steps + 1] = 0;
        amplitudeArray = newTable;
        steps = new_steps;
    }

    public void inversePolarity() {
        if (!Double.isNaN(amplitude)) {
            amplitude = -amplitude;
        }
        if (amplitudeArray != null) {
            // flow Comp
            for (int i = 0; i < steps; i++) {
                amplitudeArray[i] = -amplitudeArray[i];
            }
        }
    }


    //    Extract traj ordering from traj list of plugin
    public void reoderPhaseEncoding(TransformPlugin plugin) {
        int[] traj = plugin.invTransf(0, 0, 0, 0); // fake input
        double[] newTable = new double[traj.length / 2];
        System.out.println("traj.size() " + traj.length);
        for (int j = 0; j < traj.length / 2; j++) {
            newTable[j] = amplitudeArray[traj[j * 2]];
        }
        amplitudeArray = newTable;
        steps = traj.length / 2;

        // --------- TODO : ones plugin.getSize() will be implemented replace the code above by the following lines :
/*        int[] kpos;
        int[] sizeData = plugin.getSize(); // plugin.getScanDimension()?
        double[] newTable = new double[sizeData[1]];
        for (int scan2D = 0; scan2D < sizeData[1]; scan2D++) {
            kpos = plugin.transf(0, scan2D, 0, 0);
            newTable[scan2D] = amplitudeArray[kpos[2]];
            // System.out.println(scan2D + "  " + kpos[1] + "   " + kpos[2] + "   ");
            //  System.out.println(scan2D + "  " + kpos[1] + "   " + kpos[2] + "   |  "+traj[scan2D * 2 ]+"  "+traj[scan2D * 2 + 1]);
        }
        steps =  sizeData[1];
        amplitudeArray = newTable;*/
    }

    //    Extract traj ordering from traj list of plugin
    public void reoderPhaseEncoding3D(TransformPlugin plugin) {

        int[] traj = plugin.invTransf(0, 0, 0, 0); // fake input
        double[] newTable = new double[traj.length / 2];
        System.out.println("traj.size() " + traj.length);
        for (int j = 0; j < traj.length / 2; j++) {
            newTable[j] = amplitudeArray[traj[j * 2 + 1]];
            //System.out.println(traj[j * 2 ]+"   "+traj[j * 2 + 1]);
        }
        steps = traj.length / 2;
        amplitudeArray = newTable;


        // --------- TODO : ones plugin.getSize() will be implemented replace the code above by the following lines :
/*        int[] kpos;
        int[] sizeData = plugin.getSize();
        double[] newTable = new double[sizeData[1]];
        for (int scan2D = 0; scan2D < sizeData[1]; scan2D++) {
            kpos = plugin.transf(0, scan2D, 0, 0);
            newTable[scan2D] = amplitudeArray[kpos[2]];
            // System.out.println(scan2D + "  " + kpos[1] + "   " + kpos[2] + "   ");
            //  System.out.println(scan2D + "  " + kpos[1] + "   " + kpos[2] + "   |  "+traj[scan2D * 2 ]+"  "+traj[scan2D * 2 + 1]);
        }
        steps =  sizeData[2];*/
    }


    public void reoderPhaseEncoding(TransformPlugin plugin, int echoTrainLength, int acquisitionMatrixDimension2D, int acquisitionMatrixDimension1D) {
        // flow Comp
        if (gradFlowComp != null) {
            gradFlowComp.reoderPhaseEncoding(plugin, echoTrainLength, acquisitionMatrixDimension2D, acquisitionMatrixDimension1D);
        }
        double loopNumber, indexNew;
        if (amplitudeArray != null) {
            double[] newTable = new double[acquisitionMatrixDimension2D];
            int[] tmp = Centric(acquisitionMatrixDimension2D);
            for (int j = 0; j < acquisitionMatrixDimension2D; j++) {
                int[] indexScan = plugin.invTransf(0, j, 0, 0);
                if ("Centric4D".equalsIgnoreCase(plugin.getName())) {
                    indexScan[1] = tmp[j];
                }
                loopNumber = indexScan[0] / acquisitionMatrixDimension1D; // Echo-block number: ETL-loop index
                indexNew = indexScan[1] * echoTrainLength + loopNumber;    // indexScan[1]: index de Nb 2D
                newTable[(int) indexNew] = amplitudeArray[j];
            }
            amplitudeArray = newTable;
        }
    }

    public void reoderPhaseEncoding3D(TransformPlugin plugin, int acquisitionMatrixDimension3D) {
        // to modify , flow Comp
        if (gradFlowComp != null) {
            gradFlowComp.reoderPhaseEncoding3D(plugin, acquisitionMatrixDimension3D);
        }

        double indexNew;
        if (amplitudeArray != null) {
            double[] newTable = new double[acquisitionMatrixDimension3D];

            System.out.println("----- " + plugin.getName());
            int[] tmp = Centric(acquisitionMatrixDimension3D);
            for (int k = 0; k < acquisitionMatrixDimension3D; k++) {
                int[] indexScan = plugin.invTransf(0, 0, k, 0);
                //              System.out.println(j + " :  " +indexScan[0] + " " +indexScan[1]+ "        " +  plugin.transf(0, j, 0, 0)[0]+ " " +  plugin.transf(0, j, 0, 0)[1]);
                if ("Centric4D".equalsIgnoreCase(plugin.getName())) {
                    indexScan[2] = tmp[k];
//                    System.out.println(j + " :  " + indexScan[1]);
                }
                indexNew = indexScan[2];    // indexScan[1]: index de Nb 2D
                newTable[(int) indexNew] = amplitudeArray[k];
            }
            amplitudeArray = newTable;
        }
    }


    public int[] Centric(int acquisition_matrix_dimension_3D) {
        int[] k_index = new int[acquisition_matrix_dimension_3D];
        int[] tmpInv = new int[acquisition_matrix_dimension_3D];

        k_index[0] = 0;
        for (int i = 1; i < acquisition_matrix_dimension_3D; i++) {
            if (k_index[i - 1] == 0) {
                k_index[i] = 1;
            } else if (k_index[i - 1] > 0) {
                k_index[i] = -1 * k_index[i - 1];
            } else if (k_index[i - 1] < 0) {
                k_index[i] = -1 * (k_index[i - 1] - 1);
            }
        }
        for (int i = 0; i < acquisition_matrix_dimension_3D; i++) {
            k_index[i] = k_index[i] + (acquisition_matrix_dimension_3D / 2 - 1);
        }
        for (int i = 0; i < acquisition_matrix_dimension_3D; i++) {
            tmpInv[k_index[i]] = i;
        }
        return tmpInv;
    }

    // add dummy scan for the PE gradient
    public void addDummy(int dummyScan) {
        if (steps > 0) {
            double[] tmpAmp = new double[steps];
            System.arraycopy(amplitudeArray, 0, tmpAmp, 0, steps);
            amplitudeArray = new double[steps + dummyScan];
            for (int i = 0; i < dummyScan; i++) {
                amplitudeArray[i] = tmpAmp[0];
            }
            System.arraycopy(tmpAmp, 0, amplitudeArray, dummyScan, steps);
            steps = steps + dummyScan;
        }
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                  Spoiler
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public boolean addSpoiler(double gradAmplitude) {
        boolean testSpoilerSupThan100 = true;
        bStaticGradient = true;
        if (Double.isNaN(amplitude)) {
            amplitude = gradAmplitude;
        } else {
            amplitude += gradAmplitude;
        }
        calculateStaticArea();
        double[] gradMaxMin = checkGradientMax();
        if (Math.abs(gradMaxMin[0]) > 100.0) {
            amplitude = 100.0;
            spoilerExcess = gradMaxMin[0] - 100.0;
            minTopTime = ceilToSubDecimal((gradMaxMin[0] * equivalentTime - grad_shape_rise_time * 100.0) / 100.0, 5);
            testSpoilerSupThan100 = false;
        }
        return (testSpoilerSupThan100);
    }

    public boolean addSpoiler(double pixel_dimension, double factor) {
        bStaticGradient = true;
        double grad_area_spoiler = factor / ((GradientMath.GAMMA / nucleus.getRatio()) * pixel_dimension);//GradientMath.GAMMA: gamma/2pi  Hz/T-tour
        double grad_amp_spoiler = (grad_area_spoiler / equivalentTime) / gMax * 100.0;//
        return (addSpoiler(grad_amp_spoiler));
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                  Flow Compensation
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public double getMoment(double t, String type, int order) {
        double rise_time_up = rampTimeUpTable.get(0).doubleValue();
        double rise_time_down = rampTimeDownTable.get(0).doubleValue();
        double plato = flatTimeTable.get(0).doubleValue();
        double amp = amplitude;
        double moment0 = amp * (2 * (rise_time_up + rise_time_down) / Math.PI + plato);
        if (order == 0) {
            if (type.equalsIgnoreCase("slice")) {
                return moment0 / 2;
            } else if (type.equalsIgnoreCase("read")) {
                return moment0 / 2;
            } else {
                return moment0;
            }
        } else if (type.equalsIgnoreCase("slice")) {
            t = 0;
            double moment1 = amp / 8 * plato * plato + amp * (rise_time_up + rise_time_down) / 2 * (Math.PI * plato + (Math.PI - 2.0) * (rise_time_up + rise_time_down)) / Math.PI / Math.PI;
            return moment1 + t * moment0 / 2;
        } else if (type.equalsIgnoreCase("read")) {
            double moment1 = amp / 2 * ((rise_time_up + rise_time_down) / 2 * plato + plato * plato) + amp * (rise_time_up + rise_time_down) * (rise_time_up + rise_time_down) / Math.PI / Math.PI;
            return moment1 + t * moment0 / 2;
        } else {
            double moment1 = amp * (plato + (rise_time_up + rise_time_down)) * ((rise_time_up + rise_time_down) / Math.PI + plato / 2);
            return moment1 + t * moment0;
        }
    }

    public double[] getMomentBlock(int blockID) {

        switch (blockID) {
            case 1:
                return getMomentArmBlock(shapeUpTable, rampTimeUpTable);
            case 3:
                return getMomentArmBlock(shapeDownTable, rampTimeDownTable);
            default:
                return getMomentArmBlock(null, flatTimeTable);
        }
    }

    public double[] getEquivalentTimeBlock(int blockID) {
        switch (blockID) {
            case 1: // shape up
                return getEquivalentTimeBlock(shapeUpTable, rampTimeUpTable);
            case 3:// shape down
                return getEquivalentTimeBlock(shapeDownTable, rampTimeDownTable);
            default:
                return getEquivalentTimeBlock(null, flatTimeTable);
        }
    }

    public double[] getEquivalentTimeBlock(Shape shape, Table time) {
        double[] equivalentTime = new double[time.size()];
        if (shape == null) {
            for (int t = 0; t < time.size(); t++) {
                equivalentTime[t] = time.get(t).doubleValue();
            }
        } else {
            int n = shape.size();
            for (int t = 0; t < time.size(); t++) {
                double coefficient;
                double sumWeight = 0;
                for (int i = 0; i < n; i++) {
                    coefficient = shape.get(i).doubleValue();
                    sumWeight += coefficient;
                }
                double pctArea = sumWeight / (100 * n);
                equivalentTime[t] = time.get(t).doubleValue() * pctArea;
            }
        }
        return equivalentTime;
    }

    public double[] getEquivalentTimeFlat(Table time, double ratio) {
        ratio = Math.min(1.0, Math.max(0.0, ratio));
        double[] equivalentTime = new double[time.size()];
        for (int t = 0; t < time.size(); t++) {
            equivalentTime[t] = time.get(t).doubleValue() * ratio;
        }
        return equivalentTime;
    }


    // Measure the position of the Gradient centroid
    public double[] getMomentArmBlock(Shape shape, Table time) {
        double[] timeG = new double[time.size()];
        if (shape == null) {
            for (int t = 0; t < time.size(); t++) {
                timeG[t] = time.get(t).doubleValue() * 0.5;
            }
        } else {
            int n = shape.size();
            for (int t = 0; t < time.size(); t++) {
                double sumPosWeight = 0;
                double sumWeight = 0;
                double coef;
                double pos;
                for (int i = 0; i < n; i++) {
                    pos = 1 / (double) n * (1 / 2.0 + (double) i);
                    coef = shape.get(i).doubleValue();
                    sumWeight += coef;
                    sumPosWeight += coef * pos;
//                    System.out.println(i+" coef " +coef+ "    pos "+ pos+" sw " +sumWeight+ "   spw  "+ sumPosWeight);
                }
                double gPosPct = sumPosWeight / sumWeight;
//                System.out.println(gPosPct+" gPosPct " +gPosPct);
                timeG[t] = time.get(t).doubleValue() * gPosPct;
            }
        }
        return timeG;
    }

    /**
     * Calculate the time arm position of the gradients' centroid considering the total gradient area (ramp up + flat part + ramp down)
     *
     * @return centroid table
     */
    //
    public double[] getMomentArmTotal() {
        double[] mom1 = getMomentBlock(1);
        double[] eT11 = getEquivalentTimeBlock(1);
        double[] mom2 = getMomentBlock(2);
        double[] eT12 = getEquivalentTimeBlock(2);
        double[] mom3 = getMomentBlock(3);
        double[] eT13 = getEquivalentTimeBlock(3);
        int n1 = mom1.length;
        int n2 = mom2.length;
        int n3 = mom3.length;
        int n = Math.max(Math.max(n1, n2), n3);
        double[] momTotal = new double[n];
        for (int i = 0; i < n; i++) {
            int i1 = Math.min(i, n1 - 1);
            int i2 = Math.min(i, n2 - 1);
            int i3 = Math.min(i, n3 - 1);
            momTotal[i] = (eT11[i1] * mom1[i1]
                    + eT12[i2] * (mom2[i2] + getRampTimeUpTable().get(i1).doubleValue())
                    + eT13[i3] * (mom3[i3] + getRampTimeUpTable().get(i1).doubleValue() + getFlatTimeTable().get(i2).doubleValue())
            ) / (eT11[i1] + eT12[i2] + eT13[i3]);
        }
        return momTotal;
    }

    /**
     * Calculate the time position of the gradients' centroid, only ratio of the FLAT_PART and full RAMP DOWN are considered here
     *
     * @param ratio : ratio between the gradient area to compensate and the total gradient area
     * @return the centroid time for each gradient (flat part * ratio + ramp down)
     */
    public double[] getMomentArmEnd(double ratio) {
        ratio = Math.min(1.0, Math.max(0.0, ratio));
        double[] arm2 = getArmFlatBlockToEnd(flatTimeTable, ratio);
        double[] eT12 = getEquivalentTimeFlat(flatTimeTable, ratio);
        double[] arm3 = getMomentBlock(3);
        double[] eT13 = getEquivalentTimeBlock(3);
        int n1 = rampTimeUpTable.size();
        int n2 = arm2.length;
        int n3 = arm3.length;
        int n = Math.max(Math.max(n1, n2), n3);
        double[] momTotoal = new double[n];
        for (int i = 0; i < n; i++) {
            int i1 = Math.min(i, n1 - 1);
            int i2 = Math.min(i, n2 - 1);
            int i3 = Math.min(i, n3 - 1);
            momTotoal[i] = (eT12[i2] * (arm2[i2] + getRampTimeUpTable().get(i1).doubleValue())
                    + eT13[i3] * (arm3[i3] + getRampTimeUpTable().get(i1).doubleValue() + getFlatTimeTable().get(i2).doubleValue())
            ) / (eT12[i2] + eT13[i3]);
        }
        return momTotoal;
    }

    /**
     * Calculate the time position of the gradients' centroid, only full RAMP UP and ratio of FLAT_PART are considered here
     *
     * @param ratio : ratio between the gradient area to compensate and the total gradient area
     * @return the centroid time for each gradient (ramp up + flat part * ratio)
     */
    public double[] getMomentArmStart(double ratio) {
        ratio = Math.min(1.0, Math.max(0.0, ratio));
        double[] arm1 = getMomentBlock(1);
        double[] eT11 = getEquivalentTimeBlock(1);
        double[] arm2 = getArmFlatBlockfromStart(flatTimeTable, ratio);
        double[] eT12 = getEquivalentTimeFlat(flatTimeTable, ratio);
        int n1 = rampTimeUpTable.size();
        int n2 = arm2.length;
        int n3 = arm1.length;
        int n = Math.max(Math.max(n1, n2), n3);
        double[] momTotal = new double[n];
        for (int i = 0; i < n; i++) {
            int i1 = Math.min(i, n1 - 1);
            int i2 = Math.min(i, n2 - 1);
            int i3 = Math.min(i, n3 - 1);
            momTotal[i] = (eT11[i3] * (arm1[i3]
                    + eT12[i2] * (arm2[i2] + getRampTimeUpTable().get(i1).doubleValue()))
            ) / (eT12[i2] + eT11[i3]);
        }
        return momTotal;
    }


    /**
     * Measure the position of the gradients' centroid when considering only the last part of a flat gradient
     *
     * @param time  : table of Gradient application time
     * @param ratio : ratio between the gradient area to compensate and the total gradient area
     * @return the centroid time for each gradient
     */
    //
    public double[] getArmFlatBlockToEnd(Table time, double ratio) {
        ratio = Math.min(1.0, Math.max(0.0, ratio));
        double[] timeG = new double[time.size()];
        for (int t = 0; t < time.size(); t++) {
            timeG[t] = time.get(t).doubleValue() * (1 - ratio / 2.0);
        }
        return timeG;
    }

    /**
     * Measure the position of the gradients' centroid when considering only the first part of a flat gradient
     *
     * @param time  : table of Gradient application time
     * @param ratio : ratio between the gradient area to compensate and the total gradient area
     * @return the centroid time for each gradient
     */
    public double[] getArmFlatBlockfromStart(Table time, double ratio) {
        ratio = Math.min(1.0, Math.max(0.0, ratio));
        double[] timeG = new double[time.size()];
        for (int t = 0; t < time.size(); t++) {
            timeG[t] = time.get(t).doubleValue() * (ratio / 2.0);
        }
        return timeG;
    }

    // ---------------------------------------------------------------
    // ----------------- General Methods----------------------------------------------

    /**
     * Configure the sequence parameter table with specified values and loop order
     *
     * @param table  : Table name
     * @param order  : Loop order
     * @param values : Table values
     */
    private void setSequenceTableValues(Table table, Order order, double... values) {
        table.clear();
        table.setOrder(order);
        table.setLocked(true);
        for (double value : values) {
            table.add(value);
        }
    }

    /**
     * Ceil a number to a given decimal places
     *
     * @param numberToBeRounded : Double number
     * @param Order             : Digits kept after the decimal point
     */
    private double ceilToSubDecimal(double numberToBeRounded, double Order) {
        return Math.ceil(numberToBeRounded * Math.pow(10, Order)) / Math.pow(10, Order);
    }
}