package org.firstinspires.ftc.teamcode.tinycmd.control.profile;

public class Profile {
    MotionState initial_state;
    MotionState final_state;
    double pose_delta;

    public Profile(MotionState initial_state, MotionState final_state) {
        this.initial_state = initial_state;
        this.final_state = final_state;
        this.pose_delta = final_state.pose - initial_state.pose;
    }
}