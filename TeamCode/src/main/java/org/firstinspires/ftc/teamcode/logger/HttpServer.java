package org.firstinspires.ftc.teamcode.logger;

import fi.iki.elonen.NanoHTTPD;

import java.io.FileInputStream;
import java.io.IOException;
import fi.iki.elonen.NanoHTTPD;

public class HttpServer extends NanoHTTPD {

    public HttpServer(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        if ("/log".equals(session.getUri())) {
            String mimeType = "text/plain";
            String filename = "latest_log.txt";
            try {
                FileInputStream fis = new FileInputStream(filename);
                return NanoHTTPD.newFixedLengthResponse(Response.Status.OK, mimeType, fis, fis.available());
            } catch (IOException e) {
                e.printStackTrace();
                return NanoHTTPD.newFixedLengthResponse(Response.Status.INTERNAL_ERROR, mimeType, "Error reading log file");
            }
        } else if ("/live".equals(session.getUri())) {
            String mimeType = "text/plain";
            StringBuilder commands = new StringBuilder();
            for (String cmd : FTCDataLogger.commandList) {
                commands.append(cmd).append("\n");
            }
            return NanoHTTPD.newFixedLengthResponse(Response.Status.OK, mimeType, commands.toString());
        } else {
            return NanoHTTPD.newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not found");
        }
    }
}
