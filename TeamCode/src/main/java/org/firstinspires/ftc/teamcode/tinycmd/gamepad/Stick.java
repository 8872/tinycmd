package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import org.firstinspires.ftc.teamcode.tinycmd.util.Updating;

import java.util.function.DoubleSupplier;

// TODO add slew rate limiter
// TODO add deadzone
public class Stick implements Updating {
    private DoubleSupplier x, y;
    private final Button button;

    public Stick(DoubleSupplier x, DoubleSupplier y, Button button) {
        this.x = x;
        this.y = y;
        this.button = button;
    }

    public void invertX() {
        x = () -> -x.getAsDouble();
    }

    public void invertY() {
        y = () -> -y.getAsDouble();
    }

    public double getX() {
        return x.getAsDouble();
    }

    public double getY() {
        return y.getAsDouble();
    }

    @Override
    public void update() {
        button.update();
    }
}
