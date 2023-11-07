package rs2d.sequence.onpulsegradslc;

import rs2d.sequence.common.Gradient;
import rs2d.spinlab.instrument.util.GradientMath;

import rs2d.spinlab.sequence.Sequence;
import rs2d.spinlab.sequence.table.Shape;
import rs2d.spinlab.sequence.table.Table;
import rs2d.spinlab.sequenceGenerator.GeneratorSequenceParamEnum;
import rs2d.spinlab.tools.table.Order;
import rs2d.spinlab.tools.utility.Nucleus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Gradient: small Common Version 1.4S
 */
public class ShapeGradient {
    private String shapeName; // Sinc, Gaussian, Chirp, Trapizoidal, Triangle
    private Table amplitudeTable1;
    private Table amplitudeTable2;
    private Table amplitudeTable3;
    private Shape shape1;
    private Shape shape2;
    private Shape shape3;
    private Table timeTable1;
    private Table timeTable2;
    private Table timeTable3;
    private double freqStart;
    private double freqEnd;
    private double gradLength;

    private double amplitude = Double.NaN;

    private int steps = -1;
    private Order order = Order.FourLoop;
    private int nbPoints = 256;
    private final double minInstructionDelay = 0.000005;
    private double maxSlewRateShape = 0.0;

    private double maxSlewRateSystem = Double.NaN;

    private double gradObjectLength = Double.NaN;


    private static double gMax = Math.abs(GradientMath.getMaxGradientStrength());
    Nucleus nucleus = Nucleus.H1;

    public ShapeGradient(String shapeName, Table amplitudeTab1, Table amplitudeTab2, Table amplitudeTab3, Shape shape1,
                         Shape shape2, Shape shape3, Table timeTable1, Table timeTable2, Table timeTable3) {
        this.shapeName = shapeName;
        this.amplitudeTable1 = amplitudeTab1;
        this.amplitudeTable2 = amplitudeTab2;
        this.amplitudeTable3 = amplitudeTab3;
        this.shape1 = shape1;
        this.shape2 = shape2;
        this.shape3 = shape3;
        this.timeTable1 = timeTable1;
        this.timeTable2 = timeTable2;
        this.timeTable3 = timeTable3;
        gMax = Math.abs(GradientMath.getMaxGradientStrength());
    }


    public static ShapeGradient createShapeGradient(Sequence sequence, String shapename, GeneratorSequenceParamEnum amplitudeTab1, GeneratorSequenceParamEnum amplitudeTab2,
                                      GeneratorSequenceParamEnum amplitudeTab3, GeneratorSequenceParamEnum shape1,
                                      GeneratorSequenceParamEnum shape2, GeneratorSequenceParamEnum shape3, GeneratorSequenceParamEnum timeTable1,
                                      GeneratorSequenceParamEnum timeTable2, GeneratorSequenceParamEnum timeTable3) {
        return new ShapeGradient(shapename, sequence.getPublicTable(amplitudeTab1.name()), sequence.getPublicTable(amplitudeTab2.name()),
                sequence.getPublicTable(amplitudeTab3.name()), (Shape) sequence.getTable(shape1.name()), (Shape) sequence.getTable(shape2.name()),
                (Shape) sequence.getTable(shape3.name()), sequence.getPublicTable(timeTable1.name()), sequence.getPublicTable(timeTable2.name()),
                sequence.getPublicTable(timeTable3.name()));
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //                  general  methods
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public double getMaxSlewRateSystem(){
        return this.maxSlewRateSystem;
    }

    public double getMaxSlewRateShape(){
        this.computeMaxSlewRateShape();
        return this.maxSlewRateShape;
    }
    public double getGradObjectLength(){
        // should be called after safetyCheck()
        if (this.gradObjectLength == Double.NaN){
            this.gradObjectLength = timeTable1.get(0).doubleValue() + timeTable2.get(0).doubleValue() +timeTable3.get(0).doubleValue();
        }
        return this.gradObjectLength;
    }

    public void initSinc(double amplitude, int nbPoints, double pulseLength ){
        this.setSequenceTableValue(this.timeTable1, pulseLength);
        this.setSequenceTableValue(this.timeTable2, minInstructionDelay);
        this.setSequenceTableValue(this.timeTable3, minInstructionDelay);
        this.amplitude = amplitude;
        this.nbPoints = nbPoints;
        this.gradLength = pulseLength;
        this.setShape();

    }
    public void initGauss(double amplitude, int nbPoints, double pulseLength){
        this.setSequenceTableValue(this.timeTable1, pulseLength);
        this.setSequenceTableValue(this.timeTable2, minInstructionDelay);
        this.setSequenceTableValue(this.timeTable3, minInstructionDelay);
        this.amplitude = amplitude;
        this.nbPoints = nbPoints;
        this.gradLength = pulseLength;
        this.setShape();

    }
    public void initChirp(double amplitude, int nbPoints, double pulseLength, double freqStart, double freqEnd){
        this.setSequenceTableValue(this.timeTable1, pulseLength);
        this.setSequenceTableValue(this.timeTable2, minInstructionDelay);
        this.setSequenceTableValue(this.timeTable3, minInstructionDelay);
        this.amplitude = amplitude;
        this.nbPoints = nbPoints;
        this.freqStart = freqStart;
        this.freqEnd = freqEnd;
        this.gradLength = pulseLength;
        this.setShape();

    }
    public void initTrapezoid(double amplitude, int nbPoints, double rampUpTime, double flatTime, double rampDownTime){
        this.setSequenceTableValue(this.timeTable1, rampUpTime);
        this.setSequenceTableValue(this.timeTable2, flatTime);
        this.setSequenceTableValue(this.timeTable3, rampDownTime);
        this.amplitude = amplitude;
        this.nbPoints = nbPoints;
        this.gradLength = rampDownTime + rampDownTime + flatTime;
        this.setShape();

    }
    public void initTriangle(double amplitude, int nbPoints, double rampUpTime, double rampDownTime){
        this.setSequenceTableValue(this.timeTable1, rampUpTime);
        this.setSequenceTableValue(this.timeTable2, rampDownTime);
        this.setSequenceTableValue(this.timeTable3, minInstructionDelay);
        this.amplitude = amplitude;
        this.nbPoints = nbPoints;
        this.gradLength = rampDownTime + rampUpTime;
        this.setShape();

    }

    public void safetyCheck(double minRiseTimeFactor){
        System.out.println("mine rise time = " + GradientMath.getShortestRiseTime(100));
        System.out.println("gmax = " + gMax);
        maxSlewRateSystem = gMax/GradientMath.getShortestRiseTime(100.0) * minRiseTimeFactor / 100.0; // in T/m/s
        if (shapeName.equalsIgnoreCase("chirp")){
            double slewRateEnvelope, gCurrent, newShapeValue;
            for (int i = 0; i < nbPoints; i++){
                slewRateEnvelope = 2 * Math.PI * this.amplitude * (freqStart + (freqEnd - freqStart) * i / nbPoints);
                gCurrent = shape1.get(i).doubleValue();
                newShapeValue = Math.min(100*maxSlewRateSystem/gMax/slewRateEnvelope, 1.0) * gCurrent;
                shape1.set(i, newShapeValue);
            }
            shape1.fireListChange();
        } else {
            System.out.println("maxSlewRateSystem = " + maxSlewRateSystem);
            System.out.println("maxSlewRate");
            if (maxSlewRateSystem < maxSlewRateShape){

                double time1 = timeTable1.get(0).doubleValue();
                double time2 = timeTable2.get(0).doubleValue();
                double time3 = timeTable3.get(0).doubleValue();
                System.out.println("time1 = " + time1);
                System.out.println("time2 = " + time2);
                System.out.println("time3 = " + time3);
                if (shapeName.equalsIgnoreCase("Trapezoid")){
                    setSequenceTableValue(timeTable1, time1 * maxSlewRateShape / maxSlewRateSystem);
                    setSequenceTableValue(timeTable2, time2 * maxSlewRateShape / maxSlewRateSystem);
                    setSequenceTableValue(timeTable3, time3 * maxSlewRateShape / maxSlewRateSystem);
                    this.gradLength = timeTable1.get(0).doubleValue() + timeTable2.get(0).doubleValue() +timeTable3.get(0).doubleValue();
                } else if (shapeName.equalsIgnoreCase("Triangle")) {
                    setSequenceTableValue(timeTable1, time1 * maxSlewRateShape / maxSlewRateSystem);
                    setSequenceTableValue(timeTable2, time2 * maxSlewRateShape / maxSlewRateSystem);
                    this.gradLength = timeTable1.get(0).doubleValue() + timeTable2.get(0).doubleValue() ;
                } else {
                    setSequenceTableValue(timeTable1, time1 * maxSlewRateShape / maxSlewRateSystem);
                    this.gradLength = timeTable1.get(0).doubleValue();
                }
            }
        }
        this.gradObjectLength = timeTable1.get(0).doubleValue() + timeTable2.get(0).doubleValue() +timeTable3.get(0).doubleValue();
    }

    public void setAmplitudeTable(){
        switch (shapeName){
            case "Sinc":
            case "Gaussian":
            case "Chirp":
                setSequenceTableValues(amplitudeTable1, Order.FourLoop, amplitude);
                break;
            case "Trapezoid":
                setSequenceTableValues(amplitudeTable1, Order.FourLoop, amplitude);
                setSequenceTableValues(amplitudeTable2, Order.FourLoop, amplitude);
                setSequenceTableValues(amplitudeTable3, Order.FourLoop, amplitude);
                break;
            case "Triangle":
                setSequenceTableValues(amplitudeTable1, Order.FourLoop, amplitude);
                setSequenceTableValues(amplitudeTable2, Order.FourLoop, amplitude);
                break;
        }
    }
    private void setShape() {

        switch (shapeName){
            case "Sinc":
                buildSincTable(shape1, 3, nbPoints,100, true, "Hamming");
                maxSlewRateShape = getMaxSlewRateShape(shape1, 0, 0);
                break;
            case "Gaussian":
                buildGaussianTable(shape1, 0.25, nbPoints, 100,false);
                maxSlewRateShape = getMaxSlewRateShape(shape1, 0, 0);
                break;
            case "Chirp":
                buildChirpTable(shape1, freqStart, freqEnd, nbPoints,100);
                break;
            case "Trapezoid":
                buildTrapezoidTable(shape1, shape2, shape3, 100, nbPoints, this.timeTable1.get(0).doubleValue(),
                        this.timeTable2.get(0).doubleValue(), this.timeTable3.get(0).doubleValue());
                maxSlewRateShape = Math.max(getMaxSlewRateShape(shape1,0, this.amplitude), getMaxSlewRateShape(shape3,this.amplitude, 0));
                break;
            case "Triangle":
                buildTriangleTable(shape1, shape2, 100, nbPoints, this.timeTable1.get(0).doubleValue(),
                        this.timeTable2.get(0).doubleValue());
                maxSlewRateShape = Math.max(getMaxSlewRateShape(shape1,0, this.amplitude), getMaxSlewRateShape(shape2,this.amplitude, 0));
                break;
        }
        nullUnusedTables();

    }
    private void computeMaxSlewRateShape() {
        switch (shapeName) {
            case "Sinc":
            case "Gaussian":
                maxSlewRateShape = getMaxSlewRateShape(shape1, 0, 0);
            case "Chirp":
                maxSlewRateShape = getMaxSlewRateShape(shape1, 0, shape1.get(shape1.size()-1).doubleValue());
                break;
           case "Triangle":
                maxSlewRateShape = Math.max(getMaxSlewRateShape(shape1, 0, this.amplitude), getMaxSlewRateShape(shape2, this.amplitude, 0));
                break;
            case "Trapezoid":
            default:
                maxSlewRateShape = Math.max(getMaxSlewRateShape(shape1, 0, this.amplitude), getMaxSlewRateShape(shape3, this.amplitude, 0));
                break;

        }
    }

    private void buildSincTable(Table table, double nbLobe, int nbPoint, int amp, boolean isAbs, String window){
        double alpha = 0.0;
        if (window.equalsIgnoreCase("Hamming")) {
            alpha = 0.46;
        } else if (window.equalsIgnoreCase("Hanning")) {
            alpha = 0.5;
        }

        table.clear();

        for (int i = -nbPoint / 2; i < nbPoint / 2; i++) {
            double x = (double) i / nbPoint * 2 * Math.PI;
            double sinc = amp / nbLobe * Math.sin(x * nbLobe) / x * (1 - alpha + alpha * Math.cos(x));
            if (x == 0) {
                sinc = amp;
            }
            if (isAbs) {
                sinc = Math.abs(sinc);
            }
            table.add(sinc);
        }
        if (nbPoint == 1) {
            table.clear();
            table.add(amp);
        }
        table.fireListChange();
    }



    private void buildGaussianTable(Table table, double width, int nbPoint, int amp, boolean isAbs){
        table.clear();

        for (int i = -nbPoint / 2; i < nbPoint / 2; i++) {
            double x = (double) i / nbPoint;
            double gauss = amp * Math.exp(-(x * x) / (width * width));
            if (isAbs) {
                gauss = Math.abs(gauss);
            }
            table.add(gauss);
        }
        if (nbPoint == 1) {
            table.clear();
            table.add(amp);
        }
        table.fireListChange();

    }

    private void buildTrapezoidTable(Table table1, Table table2, Table table3, double amp, int nbPoint, double rampUpTime, double flatTime, double rampDownTime){
        table1.clear();
        table2.clear();
        table3.clear();
        double dwellTime = (rampDownTime + rampDownTime + flatTime)/ nbPoint;
        int nbPointsRampUp = (int) Math.ceil(rampUpTime / dwellTime);
        int nbPointsRampDown =  (int) Math.ceil(rampDownTime / dwellTime);
        int nbPointsFlatTime = (int) Math.ceil(flatTime / dwellTime);
        this.nbPoints = nbPointsFlatTime + nbPointsRampUp + nbPointsRampDown;
        System.out.println("rampUpTime = " + rampDownTime);
        System.out.println("rampDownTime = " + rampDownTime);
        System.out.println("flat time = "+flatTime);
        double slopeRampUp = amp/ nbPointsRampUp;
        for (int i = 0; i < nbPointsRampUp; i++){
            table1.add(slopeRampUp * (i+1));
        }
        table1.fireListChange();
        for (int i = 0; i < nbPointsFlatTime; i++){
            table2.add(amp);
        }
        table2.fireListChange();
        double slopeRampDown = amp / nbPointsRampDown;
        for (int i = 1; i <= nbPointsRampDown; i++){
            table3.add(amp - i * slopeRampDown);
        }
        table3.fireListChange();
    }

    private void buildChirpTable(Table table, double freqStart, double freqEnd, int nbPoints, double amp){
        table.clear();
        double t;
        for (int i = 0; i < nbPoints; i++){
            t = (double)i * this.gradLength/ nbPoints;
            table.add(Math.sin(2 * Math.PI * (freqStart * t + (freqEnd - freqStart) * t * t / gradLength/2 )) * amp);
        }
        table.fireListChange();
        System.out.println("chirp table size "+table.size());
    }

    private void buildTriangleTable(Table table1, Table table2, double amp, int nbPoint, double rampUpTime, double rampDownTime){
        table1.clear();
        table2.clear();
        double dwellTime = (rampDownTime + rampDownTime )/ nbPoint;
        int nbPointsRampUp = (int) Math.ceil(rampUpTime / dwellTime);
        int nbPointsRampDown =  (int) Math.ceil(rampDownTime / dwellTime);
        nbPoints = nbPointsRampUp + nbPointsRampDown;

        double slopeRampUp = amp/ nbPointsRampUp;
        for (int i = 0; i < nbPointsRampUp; i++){
            table1.add(slopeRampUp * (i+1));
        }
        table1.fireListChange();
        double slopeRampDown = amp / nbPointsRampDown;
        for (int i = 1; i <= nbPointsRampDown; i++){
            table2.add(amp - i * slopeRampDown);
        }
        table2.fireListChange();
    }
    private void nullUnusedTables(){
        switch (shapeName){
            case "Sinc":
            case "Gaussian":
            case "Chirp":
                shape2.clear();
                shape3.clear();
                setSequenceTableValue(timeTable2, minInstructionDelay);
                setSequenceTableValue(timeTable3, minInstructionDelay);
                setSequenceTableValue(amplitudeTable2, 0.0);
                setSequenceTableValue(amplitudeTable3, 0.0);
                break;
            case "Triangle":
                shape3.clear();
                setSequenceTableValue(timeTable3, minInstructionDelay);
                setSequenceTableValue(amplitudeTable3, 0.0);
                break;
        }
    }

    private double getMaxSlewRateShape(Table shape, double ampStart, double ampEnd){
        List<Double> slewRateList = new ArrayList<>();
        slewRateList.add(this.amplitude * Math.abs(shape.get(0).doubleValue() - ampStart) /10000.0);
        for (int i = 0; i < shape.size() - 1; i++){
            slewRateList.add(this.amplitude * Math.abs(shape.get(i+1).doubleValue() - shape.get(i).doubleValue())/10000.0);
        }
        slewRateList.add(this.amplitude * Math.abs(ampEnd - shape.get(shape.size() -1).doubleValue())/10000.0);
        System.out.println("maxSlewRateShape = " + Collections.max(slewRateList) * gMax / (this.gradLength / nbPoints));
        return Collections.max(slewRateList) * gMax / (this.gradLength / nbPoints);
    }



    // ---------------------------------------------------------------
    // ----------------- General Methods----------------------------------------------
    private Table setSequenceTableValue(Table table, double... values) {
        // uses Order.One because there are no tables in this dimension: compilation issue

        table.clear();
        table.setOrder(Order.FourLoop);
        table.setLocked(true);

        for (double value : values) {
            table.add(value);
        }
        return table;
    }
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