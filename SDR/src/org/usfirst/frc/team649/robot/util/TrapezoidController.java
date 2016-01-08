package org.usfirst.frc.team649.robot.util;

public class TrapezoidController {
	double t1, t2, t3, d1, d2, d3, maxAccel, maxVelocity, distance, upperBaseDist;
	
	public static void main(String[] args) {
		TrapezoidController controller = new TrapezoidController(200, 20, 40);
		
		double elapsedTime = 0;
		
		for (int i = 0; i < 1000; i++) {
			System.out.println("time: " + elapsedTime + " d: " + controller.getDistance(elapsedTime) + " a: " + controller.getAccel(elapsedTime) + " v: " + controller.getVelocity(elapsedTime));
			elapsedTime += controller.t3 / 1000;
		}
	}
	//TODO Assumes same accel and deaccel rate
	public TrapezoidController(double distance, double maxAccel, double maxVelocity) {
		this.maxAccel = maxAccel;
		this.maxVelocity = maxVelocity;
		this.distance = distance;
		
		calculate();
		
		//TODO smart way to calculate this
		while (upperBaseDist < 0) {
			this.maxVelocity *= 0.8;
			this.maxAccel *= 0.9;
			calculate();
		}
		
		System.out.println("t1: " + t1 + " t2: " + t2 + " t3: " + t3);
		System.out.println("d1: " + d1 + " d2: " + d2 + " d3: " + d3);
	}
	
	public void calculate() {
		t1 = maxVelocity / maxAccel;	//v = u + at; t = v/a
		d1 = 0.5 * t1 * t1 * maxAccel;	//s = 0.5a(t)^2
		
		upperBaseDist = distance - 2 * d1;	//Assume same accel and deaccel rate
		
		
		t2 = upperBaseDist / maxVelocity + t1;
		d2 = d1 + upperBaseDist;
		
		t3 = t1 + t2;
		d3 = d1 + d2;
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