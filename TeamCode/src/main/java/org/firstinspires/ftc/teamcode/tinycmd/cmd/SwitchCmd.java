package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import java.util.Map;
import java.util.function.Supplier;

public class SwitchCmd extends Cmd {
    private final Map<Object, Cmd> map;
    private final Supplier<Object> supplier;
    private Cmd selected;

    public SwitchCmd(Supplier<Object> supplier, Map<Object, Cmd> map) {
        this.map = map;
        this.supplier = supplier;
    }

    @Override
    public void init() {
        selected = map.get(supplier.get());
        if (selected != null) {
            selected.init();
        }
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
