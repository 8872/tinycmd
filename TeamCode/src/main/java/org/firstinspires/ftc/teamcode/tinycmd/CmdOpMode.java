package org.firstinspires.ftc.teamcode.tinycmd;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class CmdOpMode extends OpMode {

    // TODO add internal timer and method to get current gamemode. 30 seconds it should return AUTO, then TELEOP, then endgame

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        initHardware();
    }

    @Override
    public void loop() {
        Scheduler.tick();
    }

    // TODO test initHardware
    @SuppressWarnings("rawtypes")
    private void initHardware() {
        Class clazz = getClass();
        HashSet<Field> fields = new HashSet<>();
        while (clazz != null && clazz != OpMode.class) { // only BaseOpMode and subclasses
            Collections.addAll(fields, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }

        for (Field field : fields) {
            // if the field is of type Sys
            if (Sys.class.isAssignableFrom(field.getType())) {
                System.out.println("sys name is: " + field.getName());
                HashSet<Field> hardwareFields = new HashSet<>();
                field.setAccessible(true);
                Object oc;
                try {
                    oc = Objects.requireNonNull(field.get(this));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                Class c = oc.getClass();

                System.out.println(Arrays.toString(c.getDeclaredFields()));
                while (c != null && c != Sys.class) { // Subclasses of Sys
                    Collections.addAll(hardwareFields, c.getDeclaredFields());
                    c = c.getSuperclass();
                }
                System.out.println("hardwareFields: " + hardwareFields);
                for (Field hardwareField : hardwareFields) {
                    // if the field is of type HardwareDevice
                    if (HardwareDevice.class.isAssignableFrom(hardwareField.getType())) {
                        hardwareField.setAccessible(true);
                        try {
                            // if field is not already set
                            if (hardwareField.get(oc) == null) {
                                System.out.println(hardwareField.getType() + ": " + hardwareField.getName());
                                hardwareField.set(oc, hardwareMap.get(hardwareField.getType(), hardwareField.getName()));
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }
}
