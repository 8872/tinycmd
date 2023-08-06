package org.firstinspires.ftc.teamcode.tinycmd.cmd;

public class DarwinCmd extends ParallelCmd {
    // run all commands in parallel until 1 finishes, then cancels them all
    // survival of the fittest...
    public DarwinCmd(Cmd... cmds) {
        super(cmds);
    }

    @Override
    public boolean isDone() {
        return cmds.stream().anyMatch(Cmd::isDone);
    }
}
