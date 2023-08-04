package org.firstinspires.ftc.teamcode.tinycmd.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupCmd extends Cmd {
    protected List<Cmd> cmds;
    protected boolean running = false;

    protected GroupCmd(Cmd... cmds) {
        this.cmds = new ArrayList<>();

        for (Cmd c : cmds) {
            addSys(c.getSystems());
            this.cmds.add(c);
        }
    }

    public void add(Cmd... cmds) {
        if (!running) {
            this.cmds.addAll(Arrays.asList(cmds));
        }
    }

    @Override
    public void init() {
        running = true;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
