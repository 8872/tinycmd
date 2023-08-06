package org.firstinspires.ftc.teamcode.tinycmd.math.filters;

public class LowPassFilter {
    private final double dt;
    private final double RC;
    private double alpha;
    private double y;

    public LowPassFilter(double cutoffFrequency, double samplingRate) {
        dt = 1.0 / samplingRate;
        RC = 1.0 / (2 * Math.PI * cutoffFrequency);
        alpha = dt / (RC + dt);
        y = 0.0;
    }

    public double filter(double input) {
        y = (1 - alpha) * y + alpha * input;
        return y;
    }

    public void reset() {
        y = 0.0;
    }
}
