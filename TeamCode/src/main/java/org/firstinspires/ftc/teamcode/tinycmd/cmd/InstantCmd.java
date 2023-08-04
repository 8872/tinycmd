package org.firstinspires.ftc.teamcode.tinycmd.cmd;


import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

public class InstantCmd extends CustomCmd {
    public InstantCmd(Runnable runnable, Sys... systems) {
        super(runnable, () -> {}, () -> {}, () -> {}, () -> true, systems);
    }

    public InstantCmd() {
        this(() -> {});
    }
}
