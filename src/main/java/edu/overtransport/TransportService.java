package edu.overtransport;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.road.RoadSegment;
import edu.overtransport.model.vehicles.AnimalPoweredVehicle;
import edu.overtransport.model.vehicles.Car;
import edu.overtransport.model.vehicles.Vehicle;

/**
 * Business logic for controlling the Over transport travel.
 * 
 * @author coutinho
 *
 */
public class TransportService {

	public TransportService() {
	}

	private Vehicle currentVehicle;
	private Trip currentTrip;
	private RoadSegment currentSegment;

	/**
	 * Starts a trip by reseting the trip to its origin and defining the car and
	 * trip instances
	 * 
	 * @param vehicle
	 * @param trip
	 */
	public void startTrip(Vehicle vehicle, Trip trip) {
		this.currentVehicle = vehicle;
		this.currentTrip = trip;
		currentTrip.start();
	}

	/**
	 * Checks if there is more road segment to follow
	 * 
	 * @return
	 */
	public boolean hasMoreSegments() {
		return !currentTrip.arrived();
	}

	/**
	 * Gets the next segment of the trip and makes the car to run it. This is where
	 * most of the program events happens like exception handling or trip status
	 * change.
	 * 
	 * @throws TicketingException
	 * @throws UnsuitableVehicleException
	 * @throws LackOfResourcesException
	 */
	public void driveSegment() throws TicketingException, UnsuitableVehicleException, LackOfResourcesException {
		currentSegment = currentTrip.getNextSegment();
		currentSegment.run(currentVehicle);
	}

	/**
	 * Simple state string creator
	 * 
	 * @return
	 */
	public String printState() {

		String state = "Speed \t\t:\t" + currentVehicle.getSpeed() + " km/h \n";

		if (currentVehicle instanceof Car) {
			state += "Fuel \t\t:\t" + ((Car) currentVehicle).getFuelStatus() + "% \n";
			state += "Car Name \t:\t" + ((Car) currentVehicle).getName() + "\n";
		} else if (currentVehicle instanceof AnimalPoweredVehicle) {
			state += "Strength \t\t:\t" + ((AnimalPoweredVehicle) currentVehicle).getTiredness() + "% \n";
			state += "# of Horses \t:\t " + ((AnimalPoweredVehicle) currentVehicle).numberOfHorses() + "\n";
		}

		state += "Street \t\t:\t" + currentSegment.getClass().getSimpleName() + " \n";
		state += "Name \t\t:\t" + currentSegment.getName() + " \n";
		state += "% Complete \t:\t" + currentTrip.getPercentageComplete() + "% \n";

		return state;
	}

	public RoadSegment getCurrentSegment() {
		return currentSegment;
	}

	public Trip getCurrentTrip() {
		return currentTrip;
	}

}
