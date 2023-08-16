package org.firstinspires.ftc.teamcode.tinycmd.vision.apriltags;

import com.orhanobut.logger.Logger;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.tinycmd.math.geometry.Angle;
import org.firstinspires.ftc.teamcode.tinycmd.math.geometry.PointXYZ;
import org.firstinspires.ftc.teamcode.tinycmd.math.util.NotNull;
import org.firstinspires.ftc.teamcode.tinycmd.vision.VisionLocalizer;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

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
    public AprilTagLocalizer(CameraName cameraName, int blueLeft, int blueRight, int redLeft, int redRight){
        AprilTagPositions.BLUE_LEFT.setTag(blueLeft);
        AprilTagPositions.BLUE_RIGHT.setTag(blueRight);
        AprilTagPositions.RED_LEFT.setTag(redLeft);
        AprilTagPositions.RED_RIGHT.setTag(redRight);
        if(AprilTagPositions.BLUE_LEFT.tagID == -1) Logger.e(AprilTagPositions.BLUE_LEFT.tag.toString() + " tag localizer failed to initialize");
        if(AprilTagPositions.BLUE_RIGHT.tagID == -1) Logger.e(AprilTagPositions.BLUE_RIGHT.tag.toString() + " tag localizer failed to initialize");
        if(AprilTagPositions.RED_LEFT.tagID == -1) Logger.e(AprilTagPositions.RED_LEFT.tag.toString() + " tag localizer failed to initialize");
        if(AprilTagPositions.RED_RIGHT.tagID == -1) Logger.e(AprilTagPositions.RED_RIGHT.tag.toString() + " tag localizer failed to initialize");
        builder.setCamera(cameraName);
        builder.addProcessor(aprilTag);
        visionPortal = builder.build();
    }

    @Override
    public PointXYZ getPos() {
        return pos;
    }
    @Override
    public void update() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        Logger.d(String.format("%s# April Tags Detected",currentDetections.size()));
        for( AprilTagDetection detection: currentDetections) {
            if(NotNull.isAnythingNull(detection)) {
                if(detection.id == AprilTagPositions.BLUE_LEFT.tagID) {
                    pos = new PointXYZ(AprilTagPositions.BLUE_LEFT.pos.x() + detection.ftcPose.x, AprilTagPositions.BLUE_LEFT.pos.y() + detection.ftcPose.y, new Angle(AprilTagPositions.BLUE_LEFT.pos.z().deg()+ detection.ftcPose.pitch));
                }
            }
        }
    }
}


