package edu.overtransport.model;

import java.util.List;

import edu.overtransport.model.road.Road;

public class Trip {
	private String destinationName;
	private List<Road> segmentsToReach;
	private int currentSegmentIndex = 0;

	public Trip(String name, List<Road> segments) {
		this.destinationName = name;
		this.segmentsToReach = segments;
	}

	public Road getNextSegment() {
		return segmentsToReach.get(currentSegmentIndex++);
	}

	public boolean arrived() {
		return segmentsToReach.size() == (currentSegmentIndex);
	}

	public String getDestinationName() {
		return destinationName;
	}

	public short getPercentageComplete() {
		return (short) (currentSegmentIndex * 100 / (segmentsToReach.size()));
	}

}
