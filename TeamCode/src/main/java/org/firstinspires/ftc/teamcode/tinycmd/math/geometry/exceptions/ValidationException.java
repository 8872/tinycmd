package org.firstinspires.ftc.teamcode.tinycmd.math.geometry.exceptions;

import com.orhanobut.logger.Logger;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        Logger.d(message);
    }
}