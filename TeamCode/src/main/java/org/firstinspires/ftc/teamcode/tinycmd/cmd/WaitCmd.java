package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import com.qualcomm.robotcore.util.ElapsedTime;


public class WaitCmd extends Cmd {
    private final ElapsedTime time;
    private final double seconds;

    public WaitCmd(double seconds) {
        this.seconds = seconds;
        time = new ElapsedTime();
    }

    @Override
    public boolean isDone() {
        return time.time() > seconds;
    }
}
