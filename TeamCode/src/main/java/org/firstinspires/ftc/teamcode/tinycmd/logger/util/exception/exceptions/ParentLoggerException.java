package org.firstinspires.ftc.teamcode.tinycmd.logger.util.exception.exceptions;

public class ParentLoggerException extends Exception {
    public ParentLoggerException() {}

    public ParentLoggerException(String message) {
        super(message);
    }

    public ParentLoggerException(int errorCode) {
        super("Error Code: " + errorCode);
    }
}
