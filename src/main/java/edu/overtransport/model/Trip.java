package edu.overtransport.model;

import java.util.List;

import edu.overtransport.model.road.RoadSegment;

public class Trip {
	private String destinationName;
	private List<RoadSegment> segmentsToReach;
	private int currentSegmentIndex = 0;

	public Trip(String name, List<RoadSegment> segments) {
		this.destinationName = name;
		this.segmentsToReach = segments;
	}

	public RoadSegment getNextSegment() {
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

	public List<RoadSegment> getSegmentsToReach() {
		return segmentsToReach;
	}

	public void start() {
		currentSegmentIndex = 0;

	}
}
