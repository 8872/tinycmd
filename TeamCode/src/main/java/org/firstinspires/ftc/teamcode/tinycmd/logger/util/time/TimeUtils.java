package org.firstinspires.ftc.teamcode.tinycmd.logger.util.time;


import java.time.Instant;
import java.util.Random;

public class TimeUtils {

    private static final Random random = new Random();
    public static final int RANDOM = new Random().nextInt(Integer.MAX_VALUE);
    public static String getDate() {
        return "[" + getSimpleHour() + "]";
    }

    public static String getSimpleHour() {
        java.util.Date date = new java.util.Date();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = java.sql.Date.from(Instant.now());
        }
        final StringBuilder stringBuilder = new StringBuilder();
        final int hours = date.getHours();
        final int min = date.getMinutes();
        final int sec = date.getSeconds();
        if (hours < 10) stringBuilder.append("0").append(hours).append(":");
        else stringBuilder.append(hours).append(":");
        if (min < 10) stringBuilder.append("0").append(min).append(":");
        else stringBuilder.append(min).append(":");
        if (sec < 10) stringBuilder.append("0").append(sec);
        else stringBuilder.append(sec);
        return stringBuilder.toString();
    }

    public static String getRandomId() {
        return random.nextInt(Integer.MAX_VALUE) + "-" + getSimpleHour().replace(":", "-") + "-" + RANDOM;
    }
}
