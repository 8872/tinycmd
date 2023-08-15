package org.firstinspires.ftc.teamcode.tinycmd.vision;

import com.acmerobotics.roadrunner.localization.Localizer;
import org.firstinspires.ftc.teamcode.tinycmd.math.geometry.PointXYZ;

public class VisionLocalizer {

    public PointXYZ pos;
    public PointXYZ getRobotPosition() {
        return pos;
    }
    public void update(PointXYZ pos) {
        this.pos = pos;
    }

}
