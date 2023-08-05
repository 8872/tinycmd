package org.firstinspires.ftc.teamcode.tinycmd.sys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tinycmd.CmdOpMode;
import org.firstinspires.ftc.teamcode.tinycmd.util.Updating;

public abstract class Sys implements Updating {
    private final String name = getClass().getSimpleName();
    protected Telemetry telemetry = CmdOpMode.getActiveTelemetry();

    public String getName() {
        return name;
    }
}
