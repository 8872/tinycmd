package org.firstinspires.ftc.teamcode.impl.purepursuit;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry.Line;
import org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry.Point;
import org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry.Pose;
import org.firstinspires.ftc.teamcode.impl.purepursuit.math.geometry.Vector;

@TeleOp(name = "Pure Pursuit Test", group = "Test")
public class PurePursuitTestOpMode extends LinearOpMode {
    private PurePursuitController purePursuitController;
    private MecanumDrive mecanumDrive;

    @Override
    public void runOpMode() {
        // Initialize hardware and mecanum drive
        mecanumDrive = new MecanumDrive(hardwareMap);

        // Create the PurePursuitController with appropriate coefficients
        double kx = 0.5;
        double ky = 0.5;
        double radiusK = 0.05;
        double maxRadius = 15.0;
        purePursuitController = new PurePursuitController(kx, ky, radiusK, maxRadius);

        // Define the triangular path as a sequence of waypoints (replace these points with your desired path)
        Point[] waypoints = {
                new Point(0, 0),
                new Point(50, 0),
                new Point(25, 50),
                new Point(0, 0) // Back to the starting point to complete the triangle
        };

        waitForStart();

        // Loop through the waypoints to make the robot follow the triangular path
        for (int i = 0; i < waypoints.length - 1; i++) {
            Point start = waypoints[i];
            Point end = waypoints[i + 1];
            Line pathSegment = new Line(start, end);

            // Continue moving along the current segment until we are close to the next waypoint
            while (!isCloseToTarget(start, end)) {
                // Get the current robot position and orientation
                Pose currentPose = getCurrentPose();

                // Update the PurePursuitController to make the robot follow the current path segment
                Vector velocityOutput = purePursuitController.getOutputVelocity(currentPose, pathSegment);

                // Set mecanum drive powers based on the PurePursuitController velocity outputs
                setMecanumDriveVelocity(velocityOutput);

                // Sleep for a short interval (e.g., 10 milliseconds) to allow the robot to move
                sleep(10);
            }

            // Stop the robot briefly at each waypoint
            mecanumDrive.setMotorPowers(0, 0);
            sleep(500);
        }

        // Stop the robot at the end of the path
        mecanumDrive.setMotorPowers(0, 0);
    }

    private boolean isCloseToTarget(Point start, Point end) {
        // Assume the robot has mecanum wheels and encoders on each motor.
        // Use the average of all four motor encoder values to estimate the robot's distance traveled.
        int averageEncoderTicks = (mecanumDrive.frontLeft.getCurrentPosition() +
                mecanumDrive.frontRight.getCurrentPosition() +
                mecanumDrive.rearLeft.getCurrentPosition() +
                mecanumDrive.rearRight.getCurrentPosition()) / 4;

        // Convert the average encoder ticks to inches using the inchesToTicks function from MecanumDrive.
        double distanceTraveledInInches = MecanumDrive.inchesToTicks(averageEncoderTicks);

        // Calculate the distance between the robot's current position and the target end point.
        double distanceToEnd = start.distanceTo(end);

        // Define a tolerance for considering the target reached (adjust as needed).
        double toleranceInInches = 1.0;

        // Return true if the robot is within the tolerance distance to the target end point.
        return distanceToEnd - distanceTraveledInInches <= toleranceInInches;
    }

    private Pose getCurrentPose() {
        // Assume the robot has mecanum wheels and encoders on each motor.
        // Use the average of all four motor encoder values to estimate the robot's distance traveled.
        int averageEncoderTicks = (mecanumDrive.frontLeft.getCurrentPosition() +
                mecanumDrive.frontRight.getCurrentPosition() +
                mecanumDrive.rearLeft.getCurrentPosition() +
                mecanumDrive.rearRight.getCurrentPosition()) / 4;

        // Convert the average encoder ticks to inches using the inchesToTicks function from MecanumDrive.
        double distanceTraveledInInches = MecanumDrive.inchesToTicks(averageEncoderTicks);

        // Use the mecanumDrive object to get the robot's current orientation.
        double robotOrientation = mecanumDrive.getCurrentOrientation();

        // Use the robot's starting position (assuming (0, 0) as starting point) and the calculated distance traveled and orientation
        // to estimate the current robot pose.
        return new Pose(new Point(distanceTraveledInInches, 0), robotOrientation);
    }


    private void setMecanumDriveVelocity(Vector velocityOutput) {
        // Convert velocity components to motor powers and set the mecanum drive
        mecanumDrive.setMotorPowers(velocityOutput.getX(), velocityOutput.getY());
    }
}
