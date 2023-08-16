package org.firstinspires.ftc.teamcode.tinycmd.vision.apriltags;

import org.firstinspires.ftc.teamcode.tinycmd.math.geometry.PointXYZ;

public enum AprilTagPositions {

    BLUE_LEFT(-72,-36, -90, AprilTagFamily.TAG_36h11, -1),
    BLUE_RIGHT(72, -36, 90, AprilTagFamily.TAG_36h11, -1),
    RED_LEFT(-72, 36, -90, AprilTagFamily.TAG_36h11, -1),
    RED_RIGHT(72, 36, 90, AprilTagFamily.TAG_36h11, -1);
    public final PointXYZ pos;
    public final AprilTagFamily tag;
    public int tagID;
    AprilTagPositions(int x, int y, int z, AprilTagFamily tag, int tagID) {
        pos = new PointXYZ(x, y, z);
        this.tag = tag;
        this.tagID = tagID;
    }

    public void setTag(int tag) {
        tagID = tag;
    }
}
