package org.usfirst.frc.team649.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class TrapezoidalTrajectoryGenerator {
	double t1, t2, t3, d1, d2, d3, maxAccel, maxVelocity, distance, upperBaseDist, direction;
	static Timer time;
	Trajectory t;
	
	//TODO Assumes same accel and deaccel rate
	public TrapezoidalTrajectoryGenerator(double distance, double maxVelocity, double maxAccel) {
		this.maxAccel = maxAccel;
		this.maxVelocity = maxVelocity;
		this.distance = distance;
		time = new Timer();		
	}
	
	public Trajectory calculateTrajectory() {
		time.reset();
		time.start();
		
		//double elapsedTime = 0;
		
		t = new Trajectory(0);
		Trajectory.Segment seg = new Trajectory.Segment();
		
		if(distance > 0) {
			direction = 1.0;
		} else {
			direction = -1.0;
		}
		
		while(seg.pos != distance) {
			seg = calculateSegment();
			t.appendSegment(seg);
		}
		
		return t;
		
	}
	
	public Trajectory.Segment calculateSegment() {
		
		if(upperBaseDist < 0) {
			this.maxVelocity *= 0.8;
			this.maxAccel *= 0.9;
			calculateSegment();
		}
		
		t1 = maxVelocity / maxAccel;	//v = u + at; t = v/a
		d1 = 0.5 * t1 * t1 * maxAccel;	//s = 0.5a(t)^2
		
		upperBaseDist = distance - 2 * d1;	//Assume same accel and deaccel rate
		
		
		t2 = upperBaseDist / maxVelocity + t1;
		d2 = d1 + upperBaseDist;
		
		t3 = t1 + t2;
		d3 = d1 + d2;
		double segmentTime = time.get();
		
		Trajectory.Segment seg = new Trajectory.Segment(this.getDistance(segmentTime), 
				this.getVelocity(segmentTime), this.getAccel(segmentTime), direction, segmentTime/t.getNumSegments());

		return seg;
	}
	
	public double getAccel(double time) {
		if (time < t1) {
			return maxAccel;
		} else if (time < t2) {
			return 0;
		} else {
			return -maxAccel;
		}
	}
	
	public double getVelocity(double time) {
		if (time < t1) {
			double value = getAccel(time) * time;
			return Math.max(-maxVelocity, Math.min(maxVelocity, value));
		} else if (time < t2) {
			return maxVelocity;
		} else {
			double value = (getAccel(time) * (time - t2)) + maxVelocity;		//TODO assumes max velocity was reached
			return Math.max(-maxVelocity, Math.min(maxVelocity, value));
		}
	}
	
	public double getDistance(double time) {
		if (time < t1) {
			return 0.5 * maxAccel * time * time;
		} else if (time < t2) {
			return d1 + maxVelocity * (time - t1);
		} else {
			return d2 + 0.5 * maxAccel * (time - t2) * (time - t2);
		}
	}
}