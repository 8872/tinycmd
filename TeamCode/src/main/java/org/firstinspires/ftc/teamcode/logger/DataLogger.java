package org.firstinspires.ftc.teamcode.logger;

import android.annotation.SuppressLint;
import org.firstinspires.ftc.teamcode.util.GameMode;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DataLogger {
    public static List<String> commandList;
    private FileWriter logFile;
    private HttpServer server;
    private String currentDateTime;

    public DataLogger(GameMode gameMode) {
        commandList = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        currentDateTime = dateFormat.format(new Date());
    }

    public void start() {
        openLogFile();
        startHttpServer();
    }

    public void stop() {
        closeLogFile();
        stopHttpServer();
    }

    private void openLogFile() {
        try {
            String filename = "log_" + currentDateTime + ".txt";
            logFile = new FileWriter(filename, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeLogFile() {

        try {
            if (logFile != null) {
                logFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String data) {
        commandList.add(data); // Store the data in the list
        try {
            if (logFile != null) {
                logFile.write(data + "\n");
                logFile.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startHttpServer() {
        try {
            server = new HttpServer(8080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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