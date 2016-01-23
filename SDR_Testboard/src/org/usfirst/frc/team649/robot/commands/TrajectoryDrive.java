package org.usfirst.frc.team649.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.util.Trajectory;
import org.usfirst.frc.team649.robot.util.TrajectoryFollower;
import org.usfirst.frc.team649.robot.util.TrapezoidalTrajectoryGenerator;

/**
 *
 */
public class TrajectoryDrive extends Command {

	double direction;
	double kTurn = -3.0 / 80.0;
	Trajectory trajectory;
	TrajectoryFollower follower;
	TrapezoidalTrajectoryGenerator pathGenerator;
	double power;

	public TrajectoryDrive() {
		init();
	}

	public TrajectoryDrive(double dist, double maxVel, double maxAccel) {
		//feed system limitiations to new trajectory generator
		pathGenerator = new TrapezoidalTrajectoryGenerator(dist, maxVel, maxAccel);
		follower = new TrajectoryFollower("SDR Motion Profile Controller");
		init();
	}
	private void init() {
		//set constants kp, ki, kd, kvel, kaccel
		
		follower.configure(.0, 0.00, 0.00, .5, .25);
	}

	//load a motion profile for the Trajectory Follower to follow.
	public void loadProfile(Trajectory profile, double direction) {
		reset();
		follower.setTrajectory(profile);
		this.direction = direction;
	}

	public void loadProfileNoReset(Trajectory profile, double direction) {
		follower.setTrajectory(profile);
		this.direction = direction;
	}

	// create and set a trajectory to follow
	protected void initialize() {
		Trajectory t = pathGenerator.calculateTrajectory();
		this.setTrajectory(t);
		
	}

	// Calculate power to set based off where we are in the trajectory
	protected void execute() {
		double distance =  Robot.table.getDistance();
		power = follower.calculate(distance);
		
		if(power > .5) {
			power = .5; 
		} 
		if(power < -.5) {
			power = -.5;
		}	
		SmartDashboard.putNumber("power", power);

		Robot.table.setPower(power);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
		return follower.isFinishedTrajectory();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.table.setPower(0.0);
	}

	private void reset() {
		follower.reset();
		Robot.table.reset();
	}

	public int getFollowerCurrentSegment() {
		return follower.getCurrentSegment();
	}

	public int getNumSegments() {
		return follower.getNumSegments();
	}

	public void setTrajectory(Trajectory t) {
		follower.setTrajectory(t);
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
