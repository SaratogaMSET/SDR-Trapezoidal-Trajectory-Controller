package org.usfirst.frc.team649.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TrajectoryFollower {
	

	  private double kp_;
	  private double ki_;  // Not currently used, but might be in the future.
	  private double kd_;
	  private double kv_;
	  private double ka_;
	  private double last_error_;
	  private double cumulative_error;
	  private double current_direction = 0;
	  private int current_segment = 0;
	  private Trajectory profile_;
	  public String name;

	  public TrajectoryFollower(String name) {
	    this.name = name;
	  }

	  public void configure(double kp, double ki, double kd, double kv, double ka) {
	    kp_ = kp;
	    ki_ = ki;
	    kd_ = kd;
	    kv_ = kv;
	    ka_ = ka;
	  }

	  public void reset() {
	    last_error_ = 0.0;
	    current_segment = 0;
	  }

	  public void setTrajectory(Trajectory profile) {
	    profile_ = profile;
	  }

	  public double calculate(double distance_so_far) {
	   
	    if (current_segment < profile_.getNumSegments()) {
	      Trajectory.Segment segment = profile_.getSegment(current_segment);
	     
	      SmartDashboard.putNumber("Current Segemnt", current_segment);
	      
	      //our error is where we should be based off the calculated traj minus where we actually are
	      double error = segment.pos - distance_so_far;
	      
	      //PD loop plus vel and accel feed forward constants
	      double output = kp_ * error + ki_ * cumulative_error + kd_ * ((error - last_error_)
	              / segment.dt - segment.vel) + (kv_ * segment.vel
	              + ka_ * segment.acc);

	      last_error_ = error;
	      cumulative_error += last_error_;
	      current_direction = segment.direction;
	      //move on to the next segment
	      current_segment++;
	      SmartDashboard.putNumber(name + "FollowerSensor", distance_so_far);
	      SmartDashboard.putNumber(name + "FollowerGoal", segment.pos);
	      SmartDashboard.putNumber(name + "FollowerError", error);
	      
	      SmartDashboard.putNumber("Output", output);

	      return output;
	    } else {
	      return 0;
	    }
	  }

	  public double getDirection() {
	    return current_direction;
	  }

	  public boolean isFinishedTrajectory() {
	    return current_segment >= profile_.getNumSegments();
	  }
	  
	  public int getCurrentSegment() {
	    return current_segment;
	  }
	  
	  public int getNumSegments() {
	    return profile_.getNumSegments();
	  }
	  
}
