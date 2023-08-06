package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.tinycmd.util.Updating;

import java.util.Arrays;

public class GamepadEx implements Updating {
    public Input a, b, x, y, dpadUp, dpadDown, dpadLeft, dpadRight, leftBumper, rightBumper, start, guide, leftTrigger,
            rightTrigger;
    public Stick leftStick, rightStick;
    private final Updating[] toUpdate;

    public GamepadEx(Gamepad gamepad) {
        a = new Button(() -> gamepad.a);
        b = new Button(() -> gamepad.b);
        x = new Button(() -> gamepad.x);
        y = new Button(() -> gamepad.y);
        dpadUp = new Button(() -> gamepad.dpad_up);
        dpadDown = new Button(() -> gamepad.dpad_down);
        dpadLeft = new Button(() -> gamepad.dpad_left);
        dpadRight = new Button(() -> gamepad.dpad_right);
        leftBumper = new Button(() -> gamepad.left_bumper);
        rightBumper = new Button(() -> gamepad.right_bumper);
        start = new Button(() -> gamepad.start);
        guide = new Button(() -> gamepad.guide);

        leftTrigger = new Trigger(() -> gamepad.left_trigger);
        rightTrigger = new Trigger(() -> gamepad.right_trigger);

        leftStick = new Stick(
                () -> gamepad.left_stick_x,
                () -> gamepad.left_stick_y,
                new Button(() -> gamepad.left_stick_button)
        );
        rightStick = new Stick(
                () -> gamepad.right_stick_x,
                () -> gamepad.right_stick_y,
                new Button(() -> gamepad.right_stick_button)
        );

        toUpdate = new Updating[]{a, b, x, y, dpadUp, dpadDown, dpadLeft, dpadRight, leftBumper, rightBumper, start,
                guide, leftTrigger, rightTrigger, leftStick, rightStick};
    }

    // TODO figure out where to call gamepad update
    // needs to be called once in loop
    @Override
    public void update() {
        Arrays.stream(toUpdate).forEach(Updating::update);
    }
}
