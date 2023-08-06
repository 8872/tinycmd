package org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry;

public class Exponential {
    private double a;
    private double b;
    private double c;

    public Exponential(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double f(double x) {
        return a * Math.exp(b * x) + c;
    }
}