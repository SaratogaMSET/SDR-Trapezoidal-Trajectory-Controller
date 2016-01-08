package org.usfirst.frc.team649.robot;

import org.usfirst.frc.team649.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
 
    public OI() {
     	SmartDashboard.putData("Drive 10", new TrajectoryDrive(10));
    	SmartDashboard.putData("Drive -10", new TrajectoryDrive(-10));
    }

}

