package org.usfirst.frc.team649.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.util.Trajectory;
import org.usfirst.frc.team649.robot.util.TrajectoryFollower;
import org.usfirst.frc.team649.robot.util.TrapezoidalTrajectoryGenerator;

/**
 *
 */
public class LeverArmPID extends Command {

	PIDController lPID;
	double setpt;
	//true if encoder val is decreasing, false if it is increasing
	boolean encoderValDecreasing;
	
	public LeverArmPID(double setpoint, boolean relativePosition) {
		lPID = Robot.leverArmSubsystem.leverPID;
		
		//either a relative setpoint or an absolute setpoint
		this.setpt = relativePosition ? Robot.leverArmSubsystem.getAbsoluteEncoderDistance() + setpoint : setpoint;
		
		encoderValDecreasing =  Robot.leverArmSubsystem.getAbsoluteEncoderDistance() > setpt ? true : false;
	}

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
		lPID.enable();
		lPID.setSetpoint(setpt);
		Robot.isLeverPIDRunning = true;
		SmartDashboard.putNumber("Lever Arm Setpoint", setpt);
//		SmartDashboard.putBoolean("Encoder Decreasing", encoderValDecreasing);
	}

	@Override
	protected void execute() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		
		//cases where we should move
		boolean outsideMaxDecreasing = Robot.leverArmSubsystem.absoluteEncoder.getVoltage() > Robot.leverArmSubsystem.MAX_ABS_ENCODER_VAL && encoderValDecreasing;
		boolean outsideMinIncreasing = Robot.leverArmSubsystem.absoluteEncoder.getVoltage() < Robot.leverArmSubsystem.MIN_ABS_ENCODER_VAL && !encoderValDecreasing;
//		SmartDashboard.putBoolean("OutsideMaxDecreasing", outsideMaxDecreasing);
//		SmartDashboard.putBoolean("OutsideMinIncreasing", outsideMinIncreasing);
		//make sure we can PID the opposite direction when we cross the MAX or MIN
		return  !(outsideMaxDecreasing || Robot.leverArmSubsystem.withinRange() || outsideMinIncreasing) || lPID.onTarget();
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub
		lPID.disable();
		Robot.isLeverPIDRunning = false;
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub
		
	}


}
