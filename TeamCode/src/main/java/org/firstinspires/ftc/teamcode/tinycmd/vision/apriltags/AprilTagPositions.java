package org.firstinspires.ftc.teamcode.tinycmd.vision.apriltags;

import org.firstinspires.ftc.teamcode.tinycmd.math.geometry.PointXYZ;

public enum AprilTagPositions {

    BLUE_LEFT(-72,-36, -90, AprilTagFamily.UKNOWN),
    BLUE_RIGHT(72, -36, 90, AprilTagFamily.UKNOWN),
    RED_LEFT(-72, 36, -90, AprilTagFamily.UKNOWN),
    RED_RIGHT(72, 36, 90, AprilTagFamily.UKNOWN);
    public PointXYZ pos;
    public AprilTagFamily tag;
    AprilTagPositions(int x, int y, int z, AprilTagFamily tag) {
        pos = new PointXYZ(x, y, z);
        this.tag = tag;
    }

    public void setTag(AprilTagFamily tag) {
        this.tag = tag;
    }
}
