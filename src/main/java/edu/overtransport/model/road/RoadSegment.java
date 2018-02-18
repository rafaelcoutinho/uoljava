package edu.overtransport.model.road;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.vehicles.Vehicle;

public interface RoadSegment {

	public String getName();
	public int getSpeedLimit();

	public void run(Vehicle vehicle) throws TicketingException, UnsuitableVehicleException, LackOfResourcesException;
}
