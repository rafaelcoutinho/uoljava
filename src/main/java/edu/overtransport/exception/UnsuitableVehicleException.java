package edu.overtransport.exception;

/**
 * Exception when a Vehicle tries to run on a RoadSegment that it cannot follow.
 * 
 * @author coutinho
 *
 */
public class UnsuitableVehicleException extends Exception {

	public UnsuitableVehicleException(String message) {
		super(message);
	}

}
