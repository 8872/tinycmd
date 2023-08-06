package org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry;

public class Line {
    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Point getStartPoint() {
        return start;
    }

    public Point getEndPoint() {
        return end;
    }

    public double getSlopeX() {
        return end.getX() - start.getX();
    }

    public double getSlopeY() {
        return end.getY() - start.getY();
    }

    public double getLength() {
        return start.distanceTo(end);
    }

    public Point getAt(double t) {
        double x = start.getX() + t * getSlopeX();
        double y = start.getY() + t * getSlopeY();
        return new Point(x, y);
    }
}