package tinycmd;

import org.firstinspires.ftc.teamcode.tinycmd.Scheduler;
import org.firstinspires.ftc.teamcode.tinycmd.cmd.InstantCmd;
import org.junit.Test;

public class TestScheduler {
    @Test
    public void sampleTest() {
        Scheduler.add(new InstantCmd(() -> System.out.println("hello world")).repeat(3));
        Scheduler.tick();
        Scheduler.tick();
        Scheduler.tick();
        Scheduler.tick();
        Scheduler.tick();
    }
}
