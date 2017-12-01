package rs2d.sequence.common;

import rs2d.spinlab.hardware.controller.HardwareHandler;
import rs2d.spinlab.hardware.controller.peripherique.GradientHandlerInterface;

public class HardwareB0comp {
    private double amp = Double.NaN;
    private double phase = Double.NaN;

    public HardwareB0comp() {
        GradientHandlerInterface gradientHandler = HardwareHandler.getInstance().getGradientHandler();
        amp = gradientHandler.getB0compAmp().getValue().doubleValue();
        phase = gradientHandler.getB0compPhase().getValue().doubleValue();
    }

    public double getAmp() {
        return amp;
    }
    
    public double getB0compPhase() {
        return phase;
    }
}