package edu.overtransport.model.road;

import edu.overtransport.exception.BrokenCar;
import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.vehicles.RacingCar;
import edu.overtransport.model.vehicles.Vehicle;

public class Track implements RoadSegment {
	private String name;
	private final static int MAX_TRACK_SPEED = 40;

	public Track(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void run(Vehicle vehicle) throws UnsuitableVehicleException, LackOfResourcesException {
		// must be slow on a Track
		if (vehicle.getSpeed() > 30) {
			throw new BrokenCar("Track is not good with speed. Vehicle was at " + vehicle
					+ " km/h, above the 30km/h that is safe.");

		}
		if (vehicle instanceof RacingCar) {
			throw new UnsuitableVehicleException("Racing Car is not adapted for a Track");
		}
		vehicle.run();

	}

}
