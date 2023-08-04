package org.firstinspires.ftc.teamcode.tinycmd.cmd;


public class LinearCmd extends GroupCmd {
    private int index = 0;

    public LinearCmd(Cmd... cmds) {
        super(cmds);
    }

    @Override
    public void init() {
        super.init();
        cmds.get(0).init();
    }

    @Override
    public void loop() {
        if (cmds.get(index).isDone()) {
            cmds.get(index).lastly();
            cmds.get(++index).init();
        } else {
            cmds.get(index).loop();
        }
    }

    @Override
    public void lastly() {
        cmds.get(cmds.size() - 1).lastly();
    }

    @Override
    public void onInterrupt() {
        cmds.forEach(Cmd::onInterrupt);
    }

    @Override
    public boolean isDone() {
        return cmds.get(cmds.size() - 1).isDone() && index == cmds.size() - 1;
    }
}