package org.firstinspires.ftc.teamcode.tinycmd.sample.sys;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;
import org.firstinspires.ftc.teamcode.tinycmd.util.annotation.Hardware;

public class DriveSys extends Sys {
    @Hardware
    private DcMotor lf;
    @Hardware(name = "leftFront")
    private DcMotor lb;
    @Hardware
    private DcMotor rf;
    @Hardware
    private DcMotor rb;

    @Override
    public void update() {

    }
}
