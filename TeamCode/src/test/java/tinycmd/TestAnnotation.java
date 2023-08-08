package tinycmd;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.tinycmd.CmdOpMode;
import org.firstinspires.ftc.teamcode.tinycmd.sys.Sys;
import org.firstinspires.ftc.teamcode.tinycmd.util.annotation.Hardware;
import org.junit.Test;

public class TestAnnotation {
    public class DriveSys extends Sys {
        @Hardware
        private DcMotor lf;
        @Hardware(name = "leftFront")
        private DcMotor lb;
        @Hardware
        private DcMotor rf;
        @Hardware
        private DcMotor rb;

        @Override
        public void update() {

        }
    }

    public class TestOpMode extends CmdOpMode {
        DriveSys drive = new DriveSys();
        @Hardware(name = "amogus")
        private Servo servo;
    }

    @Test
    public void testHardware() {
        // to perform this unit test the line instantiating hardwareMap in CmdOpMode will have to be commented as telemetry
        // isn't created until its run formally on the bot
        TestOpMode a = new TestOpMode();
        a.init();
    }

}
