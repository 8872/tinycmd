package org.firstinspires.ftc.teamcode.tinycmd.logger.util.exception;

import org.firstinspires.ftc.teamcode.tinycmd.logger.util.exception.exceptions.ParentLoggerException;

public class ExceptionCatcher {



    public ExceptionCatcher() {

    }

    public interface LoggerExceptionRunnable{
        void run() throws ParentLoggerException;
    }

    public void catchIO(LoggerExceptionRunnable runnable) {
        try {
            runnable.run();
        } catch (ParentLoggerException exception) {
            System.out.println(exception.getMessage());
        }
    }
 }
