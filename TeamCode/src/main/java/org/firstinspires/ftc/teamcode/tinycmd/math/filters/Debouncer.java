package org.firstinspires.ftc.teamcode.tinycmd.math.filters;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.BooleanSupplier;

// TODO rework to have different debounce time for true -> false and false -> true
public class Debouncer {
    private final double time;
    private final ElapsedTime timer = new ElapsedTime();
    private boolean value = false;

    public Debouncer(double time) {
        this.time = time;
    }

    public boolean debounce(BooleanSupplier input) {
        if (input.getAsBoolean() == value) {
            timer.reset();
        }

        if (timer.seconds() > time) {
            value = input.getAsBoolean();
        }

        return value;
    }

    public boolean debounce(boolean input) {
        if (input != value) {
            timer.reset();
            value = input;
        }
        return timer.seconds() > time ? value : !value;
    }
}