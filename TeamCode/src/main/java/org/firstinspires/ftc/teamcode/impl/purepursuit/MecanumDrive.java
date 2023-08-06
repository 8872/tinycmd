package org.firstinspires.ftc.teamcode.impl.purepursuit;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MecanumDrive {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor rearLeft;
    public DcMotor rearRight;

    public BNO055IMU imu;



    public MecanumDrive(HardwareMap hardwareMap) {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        imu.initialize(parameters);

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        rearLeft = hardwareMap.get(DcMotor.class, "rear_left");
        rearRight = hardwareMap.get(DcMotor.class, "rear_right");
        imu = hardwareMap.get(BNO055IMU.class, "imu");


        // Set motor directions (depending on your robot's configuration)
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.FORWARD);
        rearRight.setDirection(DcMotor.Direction.REVERSE);

        // Set motor zero power behavior (if necessary)
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rearRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }
    public static int inchesToTicks(double distanceInInches) {
        double circumference = Math.PI * 1.9685 * 2;
        double rotations = distanceInInches / circumference;
        return (int) (rotations * 537.7);
    }

    public void setMotorPowers(double powerX, double powerY) {
        // Calculate motor powers for mecanum drive based on powerX and powerY
        double frontLeftPower = powerY + powerX;
        double frontRightPower = powerY - powerX;
        double rearLeftPower = powerY - powerX;
        double rearRightPower = powerY + powerX;

        // Normalize motor powers to keep them within the range of [-1, 1]
        double maxPower = Math.max(Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower)),
                Math.max(Math.abs(rearLeftPower), Math.abs(rearRightPower)));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            frontRightPower /= maxPower;
            rearLeftPower /= maxPower;
            rearRightPower /= maxPower;
        }

        // Set motor powers
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        rearLeft.setPower(rearLeftPower);
        rearRight.setPower(rearRightPower);
    }

    public float getCurrentOrientation() {
        return imu.getAngularOrientation().firstAngle;
    }



}