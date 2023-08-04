package org.firstinspires.ftc.teamcode.tinycmd.sys;

public abstract class Sys {
    private final String name = getClass().getSimpleName();

    public abstract void update();

    public String getName() {
        return name;
    }
}
