package org.firstinspires.ftc.teamcode.tinycmd;



import org.firstinspires.ftc.teamcode.tinycmd.cmd.Cmd;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

import java.util.HashSet;
import java.util.Set;

// TODO test onInterrupt
public final class Scheduler {
    // TODO replace with priority queue
    private static final Set<Cmd> cmds = new HashSet<>();

    public static void add(Cmd cmd) {
        Set<Cmd> toRemove = new HashSet<>();
        if (!cmds.contains(cmd)) {
            for (Cmd c : cmds) {
                if (c.getSystems().stream().anyMatch(sys -> cmd.getSystems().contains(sys))) {
                    if (c.isInterruptible()) {
                        toRemove.add(c);
                    } else {
                        return;
                    }
                }
            }
            toRemove.forEach(Cmd::onInterrupt);
            cmds.removeAll(toRemove);
            cmds.add(cmd);
            cmd.init();
        }
    }

    public static void tick() {
        cmds.stream().filter(Cmd::isDone).forEach(Cmd::lastly);
        cmds.removeIf(Cmd::isDone);

        // TODO merge these two lines of code into a single lambda
        cmds.forEach(Cmd::loop);
        // TODO test sys update
        cmds.forEach(cmd -> cmd.getSystems().forEach(Sys::update));
    }

    public static void clear() {
        cmds.clear();
    }

    public static Set<Cmd> getCmds() {
        return cmds;
    }
}