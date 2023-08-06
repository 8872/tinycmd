package org.firstinspires.ftc.teamcode.tinycmd;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tinycmd.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;
import org.firstinspires.ftc.teamcode.tinycmd.util.annotation.Hardware;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class CmdOpMode extends OpMode {

    // TODO add internal timer and method to get current gamemode. 30 seconds it should return AUTO, then TELEOP, then endgame

    private static Telemetry activeTelemetry;
    protected GamepadEx gamepadEx1, gamepadEx2;

    public static Telemetry getActiveTelemetry() {
        return activeTelemetry;
    }

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        activeTelemetry = telemetry;

        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

//        initHardware();


    }

    @Override
    public void loop() {
        Scheduler.tick();
//        gamepadEx1.update();
//        gamepadEx2.update();
    }

    // TODO test annotation Initialization

    /**
     * Initializes all fields annotated with {@link Hardware}.
     * ex. {@code @Hardware(name = "motor1") private DcMotor motor;}
     * @see Hardware
     */
    private void initAnnotations() {
        Class<?> currentClass = this.getClass();
        while (currentClass != OpMode.class && currentClass != Sys.class && currentClass!= null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Hardware.class)) {
                    Hardware annotation = field.getAnnotation(Hardware.class);
                    String hardwareName = annotation.name();
                    if (hardwareName.equals("variableName")) {
                        hardwareName = field.getName();
                    }
                    field.setAccessible(true);
                    try {
                        Object hardwareInstance = hardwareMap.get(field.getType(), hardwareName);
                        field.set(this, hardwareInstance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            currentClass = currentClass.getSuperclass();
        }
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
                HashSet<Field> sysFields = new HashSet<>();
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
                    Collections.addAll(sysFields, c.getDeclaredFields());
                    c = c.getSuperclass();
                }
                System.out.println("sysFields: " + sysFields);
                for (Field sysField : sysFields) {
                    // if the field is of type HardwareDevice
                    // TODO simplify/merge
                    if (HardwareDevice.class.isAssignableFrom(sysField.getType())) {
                        sysField.setAccessible(true);
                        try {
                            // if field is not already set
                            if (sysField.get(oc) == null) {
                                System.out.println(sysField.getType() + ": " + sysField.getName());
                                sysField.set(oc, hardwareMap.get(sysField.getType(), sysField.getName()));
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
