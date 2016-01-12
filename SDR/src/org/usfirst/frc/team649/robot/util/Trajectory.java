package org.usfirst.frc.team649.robot.util;

import java.util.ArrayList;

public class Trajectory {

	public static class Segment {
		public double pos, vel, acc, direction, dt;

		public Segment() {

		}

		public Segment(double pos, double vel, double acc, double direction,
				double dt) {
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
			return "pos: " + pos + "; vel: " + vel + "; acc: " + acc
					+ "; direction: " + direction;
		}
	}

	ArrayList<Trajectory.Segment> segments = new ArrayList<Trajectory.Segment>(
			0);

	// Segment[] segments_ = null;

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
		return segments.size();
	}

	public Segment getSegment(int index) {
		if (index < getNumSegments()) {
			return segments.get(index);
		} else {
			return new Segment();
		}
	}

	public void setSegment(int index, Segment segment) {
		if (index < getNumSegments()) {
			segments.set(index, segment);
		}
	}

	public ArrayList<Trajectory.Segment> getSegmentArrayList() {
		return segments;
	}

	public void scale(double scaling_factor) {
		for (int i = 0; i < getNumSegments(); ++i) {
			// TODO check this.
			segments.get(i).pos *= scaling_factor;
			segments.get(i).vel *= scaling_factor;
			segments.get(i).acc *= scaling_factor;

			// segments_[i].pos *= scaling_factor;
			// segments_[i].vel *= scaling_factor;
			// segments_[i].acc *= scaling_factor;
		}
	}

	public void append(Trajectory to_append) {
		segments.addAll(to_append.getSegmentArrayList());
	}

	public void appendSegment(Trajectory.Segment to_append) {
		segments.add(to_append);
	}

	public Trajectory copy() {
		Trajectory cloned = new Trajectory(deepCopySegments(segments));
		 
		cloned.deepCopySegments(this.segments);
		return cloned;
	}

	private ArrayList<Trajectory.Segment> deepCopySegments(ArrayList<Trajectory.Segment> tocopy) {
		ArrayList<Trajectory.Segment> copied = new ArrayList<Trajectory.Segment>(tocopy.size());
	
		//TODO check for OBE
		for (int i = 0; i < tocopy.size(); i++) {
			copied.set(i, new Segment(tocopy.get(i)));
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
