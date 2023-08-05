package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import java.util.function.BooleanSupplier;

public class StallCmd extends Cmd {
    private final BooleanSupplier condition;

    public StallCmd(BooleanSupplier condition) {
        this.condition = condition;
    }

    @Override
    public boolean isDone() {
        return condition.getAsBoolean();
    }
}
