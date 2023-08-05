package org.firstinspires.ftc.teamcode.tinycmd.gamepad;

import java.util.function.BooleanSupplier;

// TODO add debouncer
public class Button extends Input {
    private final BooleanSupplier state;

    public Button(BooleanSupplier state) {
        this.state = state;
    }

    @Override
    public boolean getAsBoolean() {
        return state.getAsBoolean();
    }
}
