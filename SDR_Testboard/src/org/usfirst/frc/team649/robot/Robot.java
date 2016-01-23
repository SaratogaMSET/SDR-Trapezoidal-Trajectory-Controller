
package org.usfirst.frc.team649.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team649.robot.commands.LeverArmPID;
import org.usfirst.frc.team649.robot.commands.VelPID;
import org.usfirst.frc.team649.robot.subsystems.LeverArmSubsystem;
import org.usfirst.frc.team649.robot.subsystems.Table;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//SB added for testing absolute encoder
	//public AnalogInput absoluteEncoder;
	
	public static LeverArmSubsystem leverArmSubsystem;
	
	public static boolean debug = false;
	public static OI oi;
	public static Table table;
	
	public static boolean isLeverPIDRunning = false;
	
	public boolean prevState11Button;
	public boolean prevState12Button;
	
	//public Victor leverPivot;
	
//	public static double MAX_ABS_ENCODER_VAL = 4.5;
//	public static double MIN_ABS_ENCODER_VAL = 0.5;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		table = new Table();
		oi = new OI();
		
		leverArmSubsystem = new LeverArmSubsystem();
		
		prevState11Button = false;
		prevState12Button = false;
//		
//		leverPivot = new Victor(1);
//		
//		absoluteEncoder = new AnalogInput(0);
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		Scheduler.getInstance().removeAll();
		table.reset();
		SmartDashboard.putString("in clacularte", "");
		SmartDashboard.putString("past while", "");
		SmartDashboard.putNumber("num of segs", 0 );
		SmartDashboard.putString("appeneded", "");

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Enc", table.getDistance());
		SmartDashboard.putNumber("CAN ENCPOSITION", table.motor.getEncPosition());
		SmartDashboard.putNumber("CAN GETPOSITION", table.motor.getPosition());
		SmartDashboard.putNumber("CAN GET", table.motor.get());
		
		
		if (oi.joy.getRawButton(1)) {
			new VelPID(300.0).start();
		}
		if(oi.joy.getRawButton(7)) {
			new VelPID(0).start();
		}
		
	
		
		//deadzone for SDR
	//	table.setPower(Math.abs(oi.joy.getY()) > 0.1 ? -oi.joy.getY()/1.3 : 0);
		
		//*********************JOYSTICK LEVER ARM*** *********************//
		//correct for range on lever arm **0 right now for testing SDR
		double armPowerVal = 0;//Math.abs(oi.joy.getY()) > 0.1 ? -oi.joy.getY()/3.0 : 0;
		
		//make sure the pid isnt running
		if (!isLeverPIDRunning){
			//keep it within range
			if (leverArmSubsystem.withinRange() || 
					(leverArmSubsystem.absoluteEncoder.getVoltage() > leverArmSubsystem.MAX_ABS_ENCODER_VAL && armPowerVal < 0) ||
							(leverArmSubsystem.absoluteEncoder.getVoltage() < leverArmSubsystem.MIN_ABS_ENCODER_VAL && armPowerVal > 0) ){
				leverArmSubsystem.leverPivot.set(armPowerVal);
			}
			else{
				leverArmSubsystem.leverPivot.set(0);
			}
		}
		
		
		//lever arm PID control
		if (oi.joy.getRawButton(11) && !prevState11Button){
			new LeverArmPID(0.5, LeverArmSubsystem.RELATIVE).start();
		}
		else if (oi.joy.getRawButton(12) && !prevState12Button){
			new LeverArmPID(-0.5, LeverArmSubsystem.RELATIVE).start();
		}
		
		
		
		//PREV STATES
		prevState11Button = oi.joy.getRawButton(11);
		prevState12Button = oi.joy.getRawButton(12);
		
		//SMART DASHBOARD
		SmartDashboard.putData("Absolute Encoder", leverArmSubsystem.absoluteEncoder);
		SmartDashboard.putNumber("Absolute Encoder getAverageVoltage", leverArmSubsystem.absoluteEncoder.getAverageVoltage());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
