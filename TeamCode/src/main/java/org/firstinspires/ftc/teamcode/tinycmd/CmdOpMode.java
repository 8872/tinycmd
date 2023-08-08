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
import java.util.Collections;
import java.util.HashSet;

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

        initHardware();


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

    private void initHardware() {
        Class<?> currentClass = getClass();
        HashSet<Field> opmodeFields = new HashSet<>();
        while (currentClass != OpMode.class && currentClass != null) {
            Collections.addAll(opmodeFields, currentClass.getDeclaredFields());
            currentClass = currentClass.getSuperclass();
        }
        System.out.println(opmodeFields);

        HashSet<Field> sysFields = new HashSet<>();
        for (Field opmodeField : opmodeFields) {
            if (HardwareDevice.class.isAssignableFrom(opmodeField.getType())) {
                createHardwareDevice(opmodeField);
            } else if (Sys.class.isAssignableFrom(opmodeField.getType())) {
                Object sysObj;
                opmodeField.setAccessible(true);
                try {
                    sysObj = opmodeField.get(this);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                Class<?> sysClass = sysObj.getClass();
                while (sysClass != Sys.class && sysClass != null) {
                    Collections.addAll(sysFields, sysClass.getDeclaredFields());
                    sysClass = sysClass.getSuperclass();
                }
            }
        }

        for (Field sysField : sysFields) {
            if (HardwareDevice.class.isAssignableFrom(sysField.getType())) {
                createHardwareDevice(sysField);
            }
        }
    }

    private void createHardwareDevice(Field field) {
        if (field.isAnnotationPresent(Hardware.class)) {
            Hardware annotation = field.getAnnotation(Hardware.class);
            String hardwareName = annotation.name();
            if (hardwareName.equals("variableName")) {
                hardwareName = field.getName();
            }
            field.setAccessible(true);
            System.out.println("instantiated: " + field.getType() + " " + hardwareName);

            // TODO test on actual bot since this code had to be commented cause hardwareMap is null when not on bot
            try {
                Object hardwareInstance = hardwareMap.get(field.getType(), hardwareName);
                field.set(this, hardwareInstance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
