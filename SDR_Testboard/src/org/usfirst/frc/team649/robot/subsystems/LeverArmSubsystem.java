// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team649.robot.subsystems;

import org.usfirst.frc.team649.robot.Robot;
import org.usfirst.frc.team649.robot.RobotMap;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LeverArmSubsystem extends PIDSubsystem {
   
    public static double P_CONST = 0.3;
	public static double I_CONST = 0.01;
	public static double D_CONST = 0;
	
	public static double MAX_ABS_ENCODER_VAL = 4.5;
	public static double MIN_ABS_ENCODER_VAL = 0.5;
	
	public static double LEVER_TOLERANCE = 0.03;
	
	public static boolean RELATIVE = true;
	
	public AnalogInput absoluteEncoder; 
	public Victor leverPivot;
	public PIDController leverPID;
   
    public LeverArmSubsystem() {
    	super("Lever Arm", P_CONST, I_CONST, D_CONST);
    	absoluteEncoder = new AnalogInput(0);
    	
    	leverPID = this.getPIDController();
    	leverPID.setAbsoluteTolerance(LEVER_TOLERANCE);
    	
    	leverPivot = new Victor(1);

    	//halEffect = new Encoder(aSource, bSource)
    }
    
    
    public boolean pastMax(){
		return getAbsoluteEncoderDistance() > MAX_ABS_ENCODER_VAL;
	}
    
    public boolean pastMin() {
    	return getAbsoluteEncoderDistance() < MIN_ABS_ENCODER_VAL;
    }
    public void initDefaultCommand() {
 
    }
    
    public double getAbsoluteEncoderDistance() {
    	return absoluteEncoder.getVoltage();
    }
    
    public void setPower(double pwr) {
    	leverPivot.set(pwr);
    }



	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return getAbsoluteEncoderDistance();
	}



	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		leverPivot.set(output);
	}
}