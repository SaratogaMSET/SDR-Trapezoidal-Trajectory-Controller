package org.usfirst.frc.team649.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    	public static final int encoderForwardChannel = 0;
    	public static final int encoderReverseChannel = 1;
    	public static final int tableMotorPort = 0;
    	
    //    tableencoder = new Encoder(21, 22, false, EncodingType.k2X);
//        LiveWindow.addSensor("Table", "encoder", tableencoder);
//        tableencoder.setDistancePerPulse(0.00942);
//        tableencoder.setPIDSourceParameter(PIDSourceParameter.kDistance);
//      
//        tablemotor = new Talon(0);
//        LiveWindow.addActuator("Table", "motor", (Talon) tablemotor);
//        

    
}
