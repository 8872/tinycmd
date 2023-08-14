package org.firstinspires.ftc.teamcode.tinycmd.logger.util.exception.exceptions;

public class UnableToLogException extends ParentLoggerException{
    public UnableToLogException() {}

    public UnableToLogException(String message) {
        super("Logger Exception. Unable to Log. Error: " + message);
    }

    public UnableToLogException(int errorCode) {
        super("Logger exception. error code:" + errorCode);
    }

    public UnableToLogException(Object className) {
        super("Logger exception. Logger error originated in "+ className.getClass().getName());
    }
}