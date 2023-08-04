package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import java.util.function.BooleanSupplier;

public class BooleanCmd extends Cmd {
    private final Cmd isTrue, isFalse;
    private final BooleanSupplier condition;
    private Cmd selected;

    public BooleanCmd(Cmd isTrue, Cmd isFalse, BooleanSupplier condition) {
        this.isTrue = isTrue;
        this.isFalse = isFalse;
        this.condition = condition;
    }

    @Override
    public void init() {
        if (condition.getAsBoolean()) {
            selected = isTrue;
        } else {
            selected = isFalse;
        }
        selected.init();
    }

    @Override
    public void loop() {
        selected.loop();
    }

    @Override
    public void lastly() {
        selected.lastly();
    }

    @Override
    public void onInterrupt() {
        selected.onInterrupt();
    }

    @Override
    public boolean isDone() {
        return selected.isDone();
    }
}
