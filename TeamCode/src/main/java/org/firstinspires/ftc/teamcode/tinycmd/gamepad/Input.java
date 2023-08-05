package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import org.firstinspires.ftc.teamcode.tinycmd.cmd.BindCmd;
import org.firstinspires.ftc.teamcode.tinycmd.cmd.BooleanCmd;
import org.firstinspires.ftc.teamcode.tinycmd.cmd.Cmd;
import org.firstinspires.ftc.teamcode.tinycmd.util.Updating;

import java.util.function.BooleanSupplier;

public abstract class Input implements Updating, BooleanSupplier {
    private boolean lastState;
    private boolean currentState;
    private boolean isToggled;
    private boolean stateChanged;

    public Input() {
        currentState = getAsBoolean();
        lastState = currentState;
    }

    public boolean isPressed() {
        return currentState;
    }

    public boolean isReleased() {
        return !currentState;
    }

    public boolean isToggled() {
        return isToggled;
    }

    public boolean isUntoggled() {
        return !isToggled;
    }

    public boolean wasJustPressed() {
        return !lastState && currentState;
    }

    public boolean wasJustReleased() {
        return lastState && !currentState;
    }

    public void onPress(Cmd cmd) {
        new BindCmd(cmd, this::wasJustPressed).schedule();
    }

    public void onRelease(Cmd cmd) {
        new BindCmd(cmd, this::wasJustReleased).schedule();
    }

    public void whilePressed(Cmd cmd) {
        new BindCmd(cmd, this::isPressed).schedule();
    }

    public void whileReleased(Cmd cmd) {
        new BindCmd(cmd, this::isReleased).schedule();
    }

    public void onPressUntilRelease(Cmd cmd) {
        new BindCmd(cmd.cancelWhen(this::isReleased), this::wasJustPressed).schedule();
    }

    public void onToggle(Cmd cmd) {
        new BindCmd(cmd, this::isToggled).schedule();
    }

    public void onToggle(Cmd cmd1, Cmd cmd2) {
        new BindCmd(new BooleanCmd(cmd1, cmd2, this::isToggled), () -> stateChanged).schedule();
    }

    public void onUntoggle(Cmd cmd) {
        new BindCmd(cmd, this::isUntoggled).schedule();
    }

    public void onUntoggle(Cmd cmd1, Cmd cmd2) {
        new BindCmd(new BooleanCmd(cmd1, cmd2, this::isUntoggled), () -> stateChanged).schedule();
    }

    @Override
    public void update() {
        lastState = currentState;
        currentState = getAsBoolean();
        stateChanged = lastState != currentState;
        if (wasJustPressed()) {
            isToggled = !isToggled;
        }
    }
}
