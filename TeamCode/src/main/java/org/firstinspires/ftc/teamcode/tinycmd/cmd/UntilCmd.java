package org.firstinspires.ftc.teamcode.tinycmd.cmd;



import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

import java.util.function.BooleanSupplier;

// TODO: Test
public class UntilCmd extends CustomCmd {
    public UntilCmd(Cmd cmd, BooleanSupplier condition) {
        super(cmd::init, cmd::loop, cmd::lastly, cmd::onInterrupt,
                condition, cmd.getSystems().toArray(new Sys[0]));
    }
}
