package org.firstinspires.ftc.teamcode.tinycmd;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.tinycmd.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;
import org.firstinspires.ftc.teamcode.tinycmd.util.annotation.Hardware;

import java.lang.reflect.Field;

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
    // TODO rework to properly work in an opmode
    // should get both hardware declared in opmode inheritance and in sys declared in opmode inheritance chain
    private void initHardware() {
        Class<?> currentClass = getClass();

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
}
