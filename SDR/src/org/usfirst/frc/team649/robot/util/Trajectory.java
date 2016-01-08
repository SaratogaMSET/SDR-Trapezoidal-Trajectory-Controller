package org.usfirst.frc.team649.robot.util;

public class Trajectory {

	public static class Segment {
		public double pos, vel, acc, jerk, direction, dt;

		public Segment() {

		}

		public Segment(double pos, double vel, double acc, double jerk,
				double direction, double dt) {
			this.pos = pos;
			this.vel = vel;
			this.acc = acc;
			this.jerk = jerk;
			this.direction = direction;
			this.dt = dt;

		}

		public Segment(Segment to_copy) {
			pos = to_copy.pos;
			vel = to_copy.vel;
			acc = to_copy.acc;
			jerk = to_copy.jerk;
			direction = to_copy.direction;
			dt = to_copy.dt;
		}

		public String toString() {
			return "pos: " + pos + "; vel: " + vel + "; acc: " + acc
					+ "; jerk: " + jerk + "; direction: " + direction;
		}
	}

	Segment[] segments_ = null;

	public Trajectory(int length) {
		segments_ = new Segment[length];
		for (int i = 0; i < length; ++i) {
			segments_[i] = new Segment();
		}
	}

	public Trajectory(Segment[] segments) {
		segments_ = segments;
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
			segments_[i].jerk *= scaling_factor;
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
			str += segment.jerk + "\t";
			str += segment.direction + "\t";
			str += "\n";
		}

		return str;
	}

	public String toStringProfile() {
		return toString();
	}

}