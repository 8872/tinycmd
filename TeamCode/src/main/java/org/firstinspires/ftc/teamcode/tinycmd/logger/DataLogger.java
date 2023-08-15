package org.firstinspires.ftc.teamcode.tinycmd.logger;

import android.annotation.SuppressLint;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import org.firstinspires.ftc.teamcode.tinycmd.logger.util.storage.Storage;
import org.firstinspires.ftc.teamcode.tinycmd.logger.util.time.TimeUtils;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;
import org.firstinspires.ftc.teamcode.tinycmd.util.GameMode;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * A simple logger for FTC robots.
 * This logger is designed to be used with the TinyCMD library.
 * @author DevMello
 * @version 0.0.1 Beta
 * @see org.firstinspires.ftc.teamcode.tinycmd.CmdOpMode
 */
public class DataLogger {
    public boolean hasStarted = false;
    public boolean debug = false;
    public String author = "8872";
    public String version = "v0.0.1 Beta";

    public String prefixString = "[" + author + "][" + version + "] ";
    public static List<String> commandList;
    private FileWriter logFile;
    private HttpServer server;
    private String currentDateTime;
    public Storage storage;
    public GameMode gameMode;


    private static List<String> futureDataPoints = new ArrayList<>();
    private static List<HardwareDevice> hardwareDevices = new ArrayList<>();

    /**
     * Creates a new DataLogger with the specified prefix name.
     * @see GameMode
     * initializing the wrong gameMode will cause the logger to not work properly when reading through the data.
     */
    public DataLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        storage = new Storage();
        commandList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        currentDateTime = dateFormat.format(new Date());
    }

    /**
     * Starts the logger and opens the log file.
     * This method should be called when the program is started.
     * THIS MUST BE CALLED AT START OF AUTO AND TELEOP PROGRAMS OR LOGGER WILL NOT WORK
     */
    public void start() {
        //writeDataPoints should write all the datapoints in futureDataPoints
        writeDataPoints(futureDataPoints.toArray(new String[futureDataPoints.size()]));
        startHttpServer();
    }

    /**
     * Stops the logger and closes the log file.
     * This method should be called when the program is stopped.
     * THIS MUST BE CALLED AT END OF AUTO AND TELEOP PROGRAMS
     */
    public void stop() {
        closeLogFile();
        stopHttpServer();
    }

    /**
     * Opens the log file for writing.
     * This method is automatically initialized when the logger is started.
     * This method should not be called manually.
     * @see #start()
     */
    private void openLogFile() {
        try {
            String filename = storage.getDir() + "/log_" + currentDateTime + ".txt";
            logFile = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the log file.
     * This method is automatically initialized when the logger is stopped.
     * This method should not be called manually.
     * @see #stop()
     */
    private void closeLogFile() {
        try {
            if (logFile != null) {
                logFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs data to the log file.
     * @param data The data to log
     * @Todo: Add multiple data types ex. int, double, etc.
     */
    public void log(String data) {
            commandList.add(TimeUtils.getDate()+prefixString +data); // Store the data in the list
    }

    /**
     * Set the game mode for the logger.
     * Gamemode should be set when creating the logger. This method should not be called.
     * If you use this then you have figured out how to transition directly from Auto to TeleOp.
     * @see #DataLogger()
     * @see GameMode
     * @param gameMode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Starts the HTTP server.
     * This server will soon be able to be used to view the log file.
     * This method is automatically initialized when the logger is started.
     * This method should not be called manually.
     * @see #start()
     */
    private void startHttpServer() {
        try {
            server = new HttpServer(8080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the HTTP server.
     * This method is automatically initialized when the logger is stopped.
     * This method should not be called manually.
     * @see #stop()
     */
    private void stopHttpServer() {
        if (server != null) {
            server.stop();
        }
    }

    /**
     * This method is written to be called when the program is started.
     * It takes all the data points that needs to be logged and writes them to the log file.
     */
    public void writeDataPoints(String[] data) {
        logFile.write(data);
    }

    public void writeDataPoints(List<String> data) {
        logFile.write(data.toArray(new String[data.size()]));
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    /**
     * This method is written to be called when the a class needs to be added to a logger.
     * The process method should be finished calling before the start method is called.
     * Logger cannot be logging at all moments otherwise it will be taking up too much processing power. We can limit this by letting the logger log every 100ms or so.
     * @param obj
     */
    public static void process(Object obj) {
        if (obj.getClass().isAnnotationPresent(Log.class)) {
            if (obj instanceof Sys) {
                Sys sys = (Sys) obj;
                List<HardwareDevice> dataPoints = new ArrayList<>();

                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (HardwareDevice.class.isAssignableFrom(field.getType())) {
                        try {
                            HardwareDevice device = (HardwareDevice) field.get(obj);
                            dataPoints.add(device);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                hardwareDevices.addAll(dataPoints);
                for (HardwareDevice device : dataPoints) {
                    futureDataPoints.add(device.getDeviceName());
                }
            }
        }
    }

    /**
     * Automatic recording of data.
     * Any class that implements Sys class and has the @Log annotation will have its data recorded.
     * This method should be called in opMode loop
     * make sure to call process method before calling this method
     * @see Sys
     * @see Log
     * @see #process(Object)
     */
    public void liveRecording() {
        List<String> data = new ArrayList<>();
        for(HardwareDevice device: hardwareDevices) {
            if(device instanceof DcMotor) {
                PhotonDcMotor motor = PhotonCore.getControlHubHAL().getMotor(((DcMotor) device).getPortNumber());
                data.add("Power: " + ((DcMotor) device).getPower() + "; Encoder: " + ((DcMotor) device).getCurrentPosition() + "; Current: " + motor.getCurrentAsync(CurrentUnit.AMPS));
            }
            if(device instanceof Servo) {
                data.add("Position: " + ((Servo) device).getPosition() + "; Direction: "+ ((Servo) device).getDirection());
            }
        }
    }
}