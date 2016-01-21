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
public class Table extends Subsystem {
   
    public Encoder encoder; 
    public CANTalon motor;
    public Encoder halEffect;

   
    public Table() {
    	encoder = new Encoder(RobotMap.encoderForwardChannel, RobotMap.encoderReverseChannel, false, EncodingType.k2X);
    	encoder.setDistancePerPulse(0.00942);
    	motor = new CANTalon(0); //ID from RoboRIO web interface
    	
    	//halEffect = new Encoder(aSource, bSource)
    	motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	
    }
    
    public void initDefaultCommand() {
 
    }
    
    
    public void reset() {
    	encoder.reset();
    }
    
    public double getDistance() {
    	return -encoder.getDistance();
    }
    
    public void setPower(double pwr) {
    	motor.set(pwr);
    }
}
