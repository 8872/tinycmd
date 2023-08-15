package org.firstinspires.ftc.teamcode.tinycmd.vision.math.geometry.exceptions;


import com.orhanobut.logger.Logger;
import org.firstinspires.ftc.teamcode.tinycmd.vision.math.geometry.Angle;

public class NullAngleException extends RuntimeException {

    public NullAngleException(String message) {
        Logger.d(message);
    }

    public static void throwIfInvalid(String message, Angle angle) {
        if (angle == null) {
            throw new NullAngleException(message);
        }
    }
}