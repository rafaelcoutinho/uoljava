package edu.overtransport.model.road;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.model.vehicles.Vehicle;

/**
 * Represents a road segment well paved with low-speed limits.
 * 
 * @author coutinho
 *
 */
public class CityStreet implements RoadSegment {

	private String name;
	private int maxSpeed;

	public CityStreet(String name, int maxSpeed) {
		this.name = name;
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Checks if the Vehicle is not faster than the speed limit
	 * 
	 * @throws TicketingException - if vehicle is too fast
	 * @throws LackOfResourcesException - if vehicle  gets out of resources
	 */
	@Override
	public void run(Vehicle vehicle) throws TicketingException, LackOfResourcesException {
		if (vehicle.getSpeed() > maxSpeed) {
			throw new TicketingException("You got a ticket, max speed is " + maxSpeed + " km/h and the vehicle was at "
					+ vehicle.getSpeed() + " km/h");
		}
		vehicle.run();

	}

	@Override
	public String getName() {
		return name;
	}

	public int getSpeedLimit() {
		return maxSpeed;
	}

}
