package org.firstinspires.ftc.teamcode.impl.purepursuit.math.controllers;

public class PID {
    private double kp;
    private double ki;
    private double kd;
    private double setpoint;
    private double integral;
    private double prevError;
    private double output;

    public PID(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.setpoint = 0.0;
        this.integral = 0.0;
        this.prevError = 0.0;
        this.output = 0.0;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public void reset() {
        integral = 0.0;
        prevError = 0.0;
        output = 0.0;
    }

    public void update(double measuredValue) {
        double error = setpoint - measuredValue;

        // Proportional term
        double pTerm = kp * error;

        // Integral term
        integral += error;
        double iTerm = ki * integral;

        // Derivative term
        double dTerm = kd * (error - prevError);
        prevError = error;

        output = pTerm + iTerm + dTerm;
    }

    public double getOutput() {
        return output;
    }
}