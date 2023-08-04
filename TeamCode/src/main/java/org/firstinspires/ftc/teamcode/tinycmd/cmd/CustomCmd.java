package org.firstinspires.ftc.teamcode.tinycmd.cmd;


import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

import java.util.function.BooleanSupplier;

public class CustomCmd extends Cmd {
    private final Runnable init, loop, lastly, onInterrupt;
    private final BooleanSupplier done;

    public CustomCmd(Runnable init, Runnable loop, Runnable lastly, Runnable onInterrupt, BooleanSupplier done,
                     Sys... systems) {
        this.init = init;
        this.loop = loop;
        this.lastly = lastly;
        this.onInterrupt = onInterrupt;
        this.done = done;
        addSys(systems);
    }

    @Override
    public void init() {
        init.run();
    }

    @Override
    public void loop() {
        loop.run();
    }

    @Override
    public void lastly() {
        lastly.run();
    }

    @Override
    public void onInterrupt() {
        onInterrupt.run();
    }

    @Override
    public boolean isDone() {
        return done.getAsBoolean();
    }
}
