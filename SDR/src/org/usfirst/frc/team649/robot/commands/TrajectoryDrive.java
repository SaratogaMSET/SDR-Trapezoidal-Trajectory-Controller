package org.usfirst.frc.team649.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

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

	public TrajectoryDrive() {
		init();
	}

	public TrajectoryDrive(double dist, double maxVel, double maxAccel) {
		pathGenerator = new TrapezoidalTrajectoryGenerator(dist, maxVel, maxAccel);
		init();
	}
	private void init() {
		//set constants
		follower.configure(.25, 0, 0, 1.0 / 15.0, 1.0 / 34.0);
	}

	public void loadProfile(Trajectory profile, double direction) {
		reset();
		follower.setTrajectory(profile);
		this.direction = direction;
	}

	public void loadProfileNoReset(Trajectory profile, double direction) {
		follower.setTrajectory(profile);
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Trajectory t = pathGenerator.calculateTrajectory();
		this.setTrajectory(t);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double distance = direction * Robot.table.getDistance();
		double speed = direction * follower.calculate(distance);
		Robot.table.setPower(speed);
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
		this.trajectory = t;
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

}
