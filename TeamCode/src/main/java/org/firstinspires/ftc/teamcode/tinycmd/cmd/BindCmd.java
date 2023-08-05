package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import java.util.function.BooleanSupplier;

public class BindCmd extends Cmd {
    private final Cmd cmd;
    private final BooleanSupplier event;

    public BindCmd(Cmd cmd, BooleanSupplier event) {
        this.cmd = cmd;
        this.event = event;
    }

    @Override
    public void loop() {
        if (event.getAsBoolean()) {
            cmd.schedule();
        }
    }

    @Override
    public boolean isDone() {
        return false;
    }
}
