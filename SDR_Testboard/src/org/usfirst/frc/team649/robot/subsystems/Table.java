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
    int profile = 0;
    double k_p = 0.2;
    double k_i = 0.1;
    double k_d = 0.1;
    double k_f = 0.0;
    double rampRate = 32.0;
    int izone = 10;
    int pulsesPerRev = 1024;
   
    public Table() {
    	encoder = new Encoder(RobotMap.encoderForwardChannel, RobotMap.encoderReverseChannel, false, EncodingType.k2X);
    	encoder.setDistancePerPulse(0.00942);
    	motor = new CANTalon(0); //ID from RoboRIO web interface
    	
    	//halEffect = new Encoder(aSource, bSource)
    	motor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
    	motor.configEncoderCodesPerRev(pulsesPerRev);
    	motor.setPID(k_p, k_i, k_d, k_f, izone, rampRate, profile);
    	
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
