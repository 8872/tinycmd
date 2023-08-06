package org.firstinspires.ftc.teamcode.impl.purepursuit;

import org.firstinspires.ftc.teamcode.impl.purepursuit.math.controllers.PID;
import org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry.*;

public class PurePursuitController {
    private double maxRadius;
    private double currentRadius;
    private double kx;
    private double ky;
    private double radiusK;
    private double t = 0;
    private boolean isStarting = true;
    private Line currentLine;
    private PID xController;
    private PID yController;
    private Exponential radiusLogistic;

    public PurePursuitController(double kx, double ky, double radiusK, double maxRadius) {
        this.kx = kx;
        this.ky = ky;
        this.radiusK = radiusK;
        this.maxRadius = maxRadius;
        xController = new PID(kx, 0.0, 0.0);
        yController = new PID(ky, 0.0, 0.0);
    }

    public Vector getOutputVelocity(Pose pose, Line currentLine) {
        this.currentLine = currentLine;
        Point targetPos = getTargetPos(pose.getPoint(), currentLine);
        xController.setSetpoint(targetPos.getX());
        yController.setSetpoint(targetPos.getY());
        xController.update(pose.getX());
        yController.update(pose.getY());


        updateRadius(currentLine.getLength());
        Vector powerVector = new Vector(xController.getOutput(), yController.getOutput());
        powerVector.rotate(-pose.getAngle());
        return powerVector;
    }

    public void updateRadius(double dis) {
        currentRadius = maxRadius * radiusLogistic.f(dis);
    }

    public Point getTargetPos(Point currentPos, Line currentLine) {
        return currentLine.getAt(solve(currentPos, currentLine));
    }

    public double solve(Point currentPos, Line currentLine) {
        double dx = currentLine.getStartPoint().getX() - currentPos.getX();
        double dy = currentLine.getStartPoint().getY() - currentPos.getY();
        double a = Math.sqrt(currentLine.getSlopeX() * currentLine.getSlopeX() + currentLine.getSlopeY() * currentLine.getSlopeY());
        double b = 2 * ((dx * currentLine.getSlopeX()) + (dy * currentLine.getSlopeY()));
        double c = dx * dx + dy * dy - Math.pow(currentRadius, 2);
        Quadratic quadratic = new Quadratic(a, b, c);
        double[] roots = quadratic.roots();
        t = roots.length > 0 ? roots[0] : 1.0;
        isStarting = t != t; // Check if t is NaN
        return t;
    }

    protected boolean hasReachedTarget() {
        return !isStarting && t > 0.99;
    }

    // Add other necessary methods and members to the PurePursuitController
}