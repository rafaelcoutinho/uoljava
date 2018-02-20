package edu.overtransport;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.road.RoadSegment;
import edu.overtransport.model.vehicles.AnimalPoweredVehicle;
import edu.overtransport.model.vehicles.Car;
import edu.overtransport.model.vehicles.Vehicle;

public class TransportService {

	public TransportService() {
	}

	private Vehicle currentVehicle;
	private Trip currentTrip;
	private RoadSegment currentSegment;

	public void startTrip(Vehicle vehicle, Trip trip) {
		this.currentVehicle = vehicle;
		this.currentTrip = trip;
	}

	public boolean hasMoreSegments() {
		return !currentTrip.arrived();
	}

	public void driveSegment() throws TicketingException, UnsuitableVehicleException, LackOfResourcesException {
		currentSegment = currentTrip.getNextSegment();
		currentSegment.run(currentVehicle);
	}

	public String printState() {
		
		String state = "Speed \t\t:\t" + currentVehicle.getSpeed() + " km/h \n";	
		
		if (currentVehicle instanceof Car) {
			state+="Fuel \t\t\t:\t" + ((Car) currentVehicle).getFuelStatus() + "% \n";
		} else if (currentVehicle instanceof AnimalPoweredVehicle) {
			state+="Animal Strength \t:\t" + ((AnimalPoweredVehicle) currentVehicle).getTiredness() + "% \n";
		}
		
		state+="Street \t\t:\t" + currentSegment.getClass().getSimpleName() + " \n";
		state+="Name \t\t:\t" + currentSegment.getName() + " \n";
		state+="% Complete \t:\t" + currentTrip.getPercentageComplete() + "% \n";
		
		return state;
	}
	
	public RoadSegment getCurrentSegment()
	{
		return currentSegment;
	}
	
	public Trip getCurrentTrip()
	{
		return currentTrip;
	}

}
