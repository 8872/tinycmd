package org.firstinspires.ftc.teamcode.tinycmd.logger;

import android.annotation.SuppressLint;
import org.firstinspires.ftc.teamcode.tinycmd.logger.util.storage.Storage;
import org.firstinspires.ftc.teamcode.tinycmd.logger.util.time.TimeUtils;
import org.firstinspires.ftc.teamcode.tinycmd.util.GameMode;

import java.io.FileWriter;
import java.io.IOException;
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

    /**
     * Creates a new DataLogger with the specified prefix name.
     * @param gameMode The gameMode to know when reading logs.
     * @see GameMode
     * initializing the wrong gameMode will cause the logger to not work properly when reading through the data.
     */
    public DataLogger(GameMode gameMode) {
        this.gameMode = gameMode;
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
        openLogFile();
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
        if (hasStarted) {
            commandList.add(TimeUtils.getDate()+prefixString +data); // Store the data in the list
            try {
                if (logFile != null) {
                    logFile.write(TimeUtils.getDate()+prefixString +data + "\n");
                    logFile.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Set the game mode for the logger.
     * Gamemode should be set when creating the logger. This method should not be called.
     * If you use this then you have figured out how to transition directly from Auto to TeleOp.
     * @see #DataLogger(GameMode)
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

    public List<String> getCommandList() {
        return commandList;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }
}