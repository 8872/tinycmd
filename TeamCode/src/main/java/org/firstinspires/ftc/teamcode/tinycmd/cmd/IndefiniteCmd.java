package org.firstinspires.ftc.teamcode.tinycmd.cmd;


import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

public class IndefiniteCmd extends CustomCmd {
    public IndefiniteCmd(Cmd cmd) {
        super(cmd::init, cmd::loop, cmd::lastly, cmd::onInterrupt,
                () -> false, cmd.getSystems().toArray(new Sys[0]));
    }
}
