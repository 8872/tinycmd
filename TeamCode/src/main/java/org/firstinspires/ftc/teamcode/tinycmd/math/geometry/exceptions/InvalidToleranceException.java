package org.firstinspires.ftc.teamcode.tinycmd.math.geometry.exceptions;

import com.orhanobut.logger.Logger;

public class InvalidToleranceException extends RuntimeException {

    public InvalidToleranceException(String s) {
        Logger.d(s);
    }

    public static void throwIfInvalid(String message, double tolerance) {
        if (tolerance < 0) throw new InvalidToleranceException(message);
    }
}