package org.firstinspires.ftc.teamcode.tinycmd.vision.apriltags;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.tinycmd.vision.VisionLocalizer;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class AprilTagLocalizer extends VisionLocalizer {

    VisionPortal.Builder builder = new VisionPortal.Builder();
    VisionPortal visionPortal;
    AprilTagProcessor aprilTag = new AprilTagProcessor.Builder()
            .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
            .setDrawAxes(false)
            .setDrawTagOutline(true)
            .build();

    /**
     * Create a new {@code AprilTagLocalizer} instance.
     * There are 4 april tags on the field.
     * You will have to give them in order and the Tag ID in the parameters
     * Look at the image to have a better understanding
     * @param cameraName
     */
    public AprilTagLocalizer(CameraName cameraName) {
        builder.setCamera(cameraName);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    public void update() {
    }
}


