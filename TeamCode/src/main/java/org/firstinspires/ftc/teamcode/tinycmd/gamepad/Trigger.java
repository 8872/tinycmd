package org.firstinspires.ftc.teamcode.tinycmd.gamepad;


import java.util.function.DoubleSupplier;

// TODO add debouncer
public class Trigger extends Input {
    private double threshold = 0.5;
    private final DoubleSupplier state;

    public Trigger(DoubleSupplier state) {
        this.state = state;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean getAsBoolean() {
        return state.getAsDouble() > threshold;
    }
}
