package org.usfirst.frc.team649.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team649.robot.commands.TrajectoryDrive;
import org.usfirst.frc.team649.robot.subsystems.Table;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static boolean debug = false;
	public static OI oi;
	public static Table table;;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		table = new Table();
		oi = new OI();

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
		if (oi.joy.getRawButton(1)) {
			new TrajectoryDrive(10, 10, 2).start();
		}
		if(oi.joy.getRawButton(7)) {
			table.reset();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
