package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.tinycmd.math.filters.Debouncer;

import java.util.function.BooleanSupplier;

// TODO add debouncer
public class Button extends Input {
    private final double waitTime = 0.2;
    private boolean toggle = false;
    private boolean tap = false;
    private boolean down = false;
    private boolean up = false;
    private boolean pressed_last_cycle = false;
    private final BooleanSupplier state;
    private ElapsedTime time = new ElapsedTime();
    private final Debouncer debouncer;
    private final Debouncer trueDebouncer;
    private final Debouncer falseDebouncer;
    private final double trueDebounceTime = 0.2;
    private final double falseDebounceTime = 0.2;


    public Button(BooleanSupplier state) {
        debouncer = new Debouncer(waitTime);
        trueDebouncer = new Debouncer(this.trueDebounceTime);
        falseDebouncer = new Debouncer(this.falseDebounceTime);
        this.state = state;
    }

    @Override
    public boolean getAsBoolean() {
        return state.getAsBoolean();
    }
    /**
     * Updates the state of the button.
     */
    public void update() {
        boolean currentState = getAsBoolean();
        boolean trueState = trueDebouncer.debounce(currentState);
        boolean falseState = falseDebouncer.debounce(!currentState);

        down = trueState;
        up = falseState;

        if (down && !tap && time.seconds() > trueDebounceTime) {
            tap = true;
            toggle = !toggle;
        } else if (!down) {
            tap = false;
        }
    }
    /*
    public void update() {
        boolean currentState = debouncer.debounce(state::getAsBoolean);

        down = currentState;
        up = !down;

        if (down && !tap && time.seconds() > debouncer.getDebounceTime()) {
            tap = true;
            toggle = !toggle;
        } else if (!down) {
            tap = false;
        }
    }
    public void update() {
        boolean currentState = state.getAsBoolean();;
        if (currentState != down) {
            time.reset();
        }
        down = currentState;
        up = !down;
        if (down && !pressed_last_cycle && time.seconds() > waitTime) {
            pressed_last_cycle = true;
            toggle = !toggle;
            tap = true;
        } else {
            tap = false;
            pressed_last_cycle = down;
        }
    }
     */


}
