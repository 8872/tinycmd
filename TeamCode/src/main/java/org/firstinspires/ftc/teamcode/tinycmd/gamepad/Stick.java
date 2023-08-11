package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import org.firstinspires.ftc.teamcode.tinycmd.util.Updating;

import java.util.function.DoubleSupplier;

// TODO add slew rate limiter
// TODO add deadzone
public class Stick implements Updating {
    private DoubleSupplier x, y;
    private final Button button;

    private boolean deadzoneEnabled = false;
    private boolean slewRateLimitEnabled = false;

    private boolean smoothEnabled = false;

    // Slew rate limits (in units per second)
    private double maxXRateLimit = 1.0;
    private double maxYRateLimit = 1.0;

    // Variables to store previous values for rate limiting
    private double prevX = 0.0;
    private double prevY = 0.0;
    private long prevTimestamp = 0;

    private double deadzoneThreshold = 0.1; // Adjust this value as needed

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
        double currentX = x.getAsDouble();
        double currentY = y.getAsDouble();
        double limitedX;
        double limitedY;
        long currentTimestamp = System.nanoTime();
        if (slewRateLimitEnabled) {
            double deltaTime = (currentTimestamp - prevTimestamp) / 1e9; // Convert nanoseconds to seconds
            double xRateLimit = maxXRateLimit * deltaTime;
            double yRateLimit = maxYRateLimit * deltaTime;
            limitedX = limitRate(prevX, currentX, xRateLimit);
            limitedY = limitRate(prevY, currentY, yRateLimit);
        } else {
            limitedX = currentX;
            limitedY = currentY;
        }
        prevX = limitedX;
        prevY = limitedY;
        prevTimestamp = currentTimestamp;
        if(deadzoneEnabled) {
            limitedX = applyDeadzone(limitedX);
            limitedY = applyDeadzone(limitedY);
        }

        double finalLimitedX = limitedX;
        x = () -> finalLimitedX;
        double finalLimitedY = limitedY;
        y = () -> finalLimitedY;
    }

    private double limitRate(double prevValue, double currentValue, double rateLimit) {
        double diff = currentValue - prevValue;
        double limitedDiff = Math.max(-rateLimit, Math.min(rateLimit, diff));
        return prevValue + limitedDiff;
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) < deadzoneThreshold) {
            return 0.0;
        }
        return value;
    }
}
