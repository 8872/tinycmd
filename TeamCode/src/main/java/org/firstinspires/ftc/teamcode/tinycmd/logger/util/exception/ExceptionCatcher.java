package org.firstinspires.ftc.teamcode.tinycmd.logger.util.exception;

import com.orhanobut.logger.Logger;
import org.firstinspires.ftc.teamcode.tinycmd.math.util.NotNull;

import java.io.IOException;

public class ExceptionCatcher {



    public ExceptionCatcher() {

    }

    public interface IOExceptionRunnable{
        void run() throws IOException;
    }

    public void catchIO(IOExceptionRunnable runnable) {
        try {
            runnable.run();
        } catch (IOException exception) {
            if (NotNull.isAnythingNull(exception)) Logger.e(exception, exception.getMessage());
        }
    }

    public interface IllegalAccessExceptionRunnable{
        void run() throws IllegalAccessException;
    }

    public void catchIllegalAccess(IllegalAccessExceptionRunnable runnable) {
        try {
            runnable.run();
        } catch (IllegalAccessException exception) {
            if (NotNull.isAnythingNull(exception)) Logger.e(exception, exception.getMessage());
        }
    }
 }
