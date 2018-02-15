package edu.overtransport.model.road;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.vehicles.Vehicle;

public class CityStreet implements Road {

	private String name;
	private int maxSpeed;

	public CityStreet(String name, int maxSpeed) {
		this.name = name;
		this.maxSpeed = maxSpeed;
	}

	@Override
	public void run(Vehicle vehicle) throws TicketingException, UnsuitableVehicleException, LackOfResourcesException {
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

}
