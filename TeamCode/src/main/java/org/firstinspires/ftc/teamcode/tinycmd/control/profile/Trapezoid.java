package org.firstinspires.ftc.teamcode.tinycmd.control.profile;

public class Trapezoid extends Profile {
    public Trapezoid(MotionState initial_state, MotionState final_state) {
        super(initial_state, final_state);
    }

    public double calculateDistance(double max_vel, double max_accel, double max_decel, double elapsed_time, boolean velocity) {
        double init_pose = this.initial_state.pose;
        double d = this.pose_delta;
        double init_vel = this.initial_state.vel;
        double final_vel = this.final_state.vel;

        double d1, decel_point;
        if (init_vel < final_vel) {
            d1 = (final_vel * final_vel - init_vel * init_vel) / (2 * max_accel);
            decel_point = d1 + (d - d1) * max_decel / (max_accel + max_decel);
        } else {
            d1 = (init_vel * init_vel - final_vel * final_vel) / (2 * max_decel);
            decel_point = (d - d1) * max_decel / (max_accel + max_decel);
        }

        double accel_t = max_vel / max_accel - init_vel / max_accel;
        double accel_d = init_vel * accel_t + 0.5 * max_accel * accel_t * accel_t;

        if (accel_d > decel_point) {
            accel_d = decel_point;
            max_vel = Math.sqrt(init_vel * init_vel + 2 * max_accel * decel_point);
            accel_t = max_vel / max_accel - init_vel / max_accel;
        }

        double decel_t = max_vel / max_decel - final_vel / max_decel;
        double decel_d = final_vel * decel_t + 0.5 * max_decel * decel_t * decel_t;

        double cruise_d = d - accel_d - decel_d;
        double cruise_t = cruise_d / max_vel;

        double t_sum = accel_t + cruise_t + decel_t;
        double decel_start = accel_t + cruise_t;

        if (elapsed_time > t_sum) {
            if (velocity) {
                return final_vel;
            }
            return init_pose + d;
        }

        if (elapsed_time < accel_t) {
            if (velocity) {
                return init_vel + max_accel * elapsed_time;
            }
            return init_pose + init_vel * elapsed_time + 0.5 * max_accel * elapsed_time * elapsed_time;
        } else if (elapsed_time < decel_start) {
            if (velocity) {
                return init_vel + max_accel * accel_t;
            }
            double current_cruise_t = elapsed_time - accel_t;
            return init_pose + accel_d + max_vel * current_cruise_t;
        } else {
            double current_decel_t = elapsed_time - decel_start;
            if (velocity) {
                return init_vel + max_accel * accel_t - max_decel * current_decel_t;
            }
            return init_pose + accel_d + cruise_d + max_vel * current_decel_t - 0.5 * max_decel * current_decel_t * current_decel_t;
        }
    }
}