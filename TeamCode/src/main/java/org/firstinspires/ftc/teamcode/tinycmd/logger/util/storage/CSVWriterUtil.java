package org.firstinspires.ftc.teamcode.tinycmd.logger.util.storage;

import android.os.Environment;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CSVWriterUtil {
    private FileWriter fileWriter;
    private boolean debug;
    CSVWriter writer;
    public CSVWriterUtil() throws IOException {
        this(generateDefaultFileName(), false);
    }

    public CSVWriterUtil(boolean debug) throws IOException {
        this(generateDefaultFileName(), debug);
    }

    public CSVWriterUtil(String fileName, boolean debug) throws IOException {
        this.debug = debug;

        if (debug) {
            fileWriter = new FileWriter(fileName);
        } else {
            File externalStorageDir = Environment.getExternalStorageDirectory();
            File dir = new File(externalStorageDir.getAbsolutePath() + "/FTC_Logs");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, fileName);
            fileWriter = new FileWriter(file);
        }
        writer = new CSVWriter(fileWriter);
    }

    private static String generateDefaultFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        return dateFormat.format(new Date()) + ".csv";
    }

    public void write(String[] data) {
        writer.writeNext(data);
    }


}
