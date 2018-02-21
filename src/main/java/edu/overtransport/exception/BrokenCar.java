package edu.overtransport.exception;

/**
 * Broken car exception thrown when a car got broken by a RoadSegment.
 * 
 * @author coutinho
 *
 */
public class BrokenCar extends UnsuitableVehicleException {

	public BrokenCar(String message) {
		super(message);
	}

}
