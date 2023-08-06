package org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry;

public class Pose {
    private Point point;
    private double angle;

    public Pose(Point point, double angle) {
        this.point = point;
        this.angle = angle;
    }

    public Point getPoint() {
        return point;
    }

    public double getX() {
        return point.getX();
    }

    public double getY() {
        return point.getY();
    }

    public double getAngle() {
        return angle;
    }
}
