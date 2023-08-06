package org.firstinspires.ftc.teamcode.tinycmd.logger.util.storage;


import java.io.File;

public class Storage {
    File dir;
    public Storage() {
         dir = setDir("/sdcard/FTC/logs", "DataLogger");
    }
    public String getDir(){
        return dir.getAbsolutePath();
    }
    private File setDir(String dirpath, String name){
        File dir = new File(dirpath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
