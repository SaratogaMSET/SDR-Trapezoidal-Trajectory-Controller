package org.usfirst.frc.team649.robot.util;

import java.util.ArrayList;

public class Trajectory {

	public static class Segment {
		public double pos, vel, acc, direction, dt;

		public Segment() {

		}

		public Segment(double pos, double vel, double acc, double direction, double dt) {
			this.pos = pos;
			this.vel = vel;
			this.acc = acc;
			this.direction = direction;
			this.dt = dt;

		}

		public Segment(Segment to_copy) {
			pos = to_copy.pos;
			vel = to_copy.vel;
			acc = to_copy.acc;
			direction = to_copy.direction;
			dt = to_copy.dt;
		}

		public String toString() {
			return "pos: " + pos + "; vel: " + vel + "; acc: " + acc + "; direction: " + direction;
		}
	}

	ArrayList<Trajectory.Segment> segments = new ArrayList<Trajectory.Segment>(0);
	
//	Segment[] segments_ = null;

	public Trajectory(int length) {
		segments = new ArrayList<Trajectory.Segment>(length);
		for (int i = 0; i < length; ++i) {
			segments.set(i, new Segment());
		}
	}

	public Trajectory(ArrayList<Trajectory.Segment> segs) {
		segments = new ArrayList<Trajectory.Segment>(segs);
	}

	public int getNumSegments() {
		return segments_.length;
	}

	public Segment getSegment(int index) {
		if (index < getNumSegments()) {
			return segments_[index];
		} else {
			return new Segment();
		}
	}

	public void setSegment(int index, Segment segment) {
		if (index < getNumSegments()) {
			segments_[index] = segment;
		}
	}

	public void scale(double scaling_factor) {
		for (int i = 0; i < getNumSegments(); ++i) {
			segments_[i].pos *= scaling_factor;
			segments_[i].vel *= scaling_factor;
			segments_[i].acc *= scaling_factor;
		}
	}

	public void append(Trajectory to_append) {
		Segment[] temp = new Segment[getNumSegments()
				+ to_append.getNumSegments()];

		for (int i = 0; i < getNumSegments(); ++i) {
			temp[i] = new Segment(segments_[i]);
		}
		for (int i = 0; i < to_append.getNumSegments(); ++i) {
			temp[i + getNumSegments()] = new Segment(to_append.getSegment(i));
		}

		this.segments_ = temp;
	}
	
	public void appendSegment(Trajectory.Segment to_append) {
		
	}

	public Trajectory copy() {
		Trajectory cloned = new Trajectory(getNumSegments());
		cloned.segments_ = copySegments(this.segments_);
		return cloned;
	}

	private Segment[] copySegments(Segment[] tocopy) {
		Segment[] copied = new Segment[tocopy.length];
		for (int i = 0; i < tocopy.length; ++i) {
			copied[i] = new Segment(tocopy[i]);
		}
		return copied;
	}

	public String toString() {
		String str = "Segment\tPos\tVel\tAcc\tJerk\tHeading\n";
		for (int i = 0; i < getNumSegments(); ++i) {
			Trajectory.Segment segment = getSegment(i);
			str += i + "\t";
			str += segment.pos + "\t";
			str += segment.vel + "\t";
			str += segment.acc + "\t";
			str += segment.direction + "\t";
			str += "\n";
		}

		return str;
	}

	public String toStringProfile() {
		return toString();
	}

}
