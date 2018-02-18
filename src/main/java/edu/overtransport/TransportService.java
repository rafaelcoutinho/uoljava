package edu.overtransport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.road.CityStreet;
import edu.overtransport.model.road.Highway;
import edu.overtransport.model.road.RoadSegment;
import edu.overtransport.model.road.Track;
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

	public void printState() {
		System.out.println("Speed             : " + currentVehicle.getSpeed());
		if (currentVehicle instanceof Car) {
			System.out.println("Fuel  		  : " + ((Car) currentVehicle).getFuelStatus() + "%");
		} else if (currentVehicle instanceof AnimalPoweredVehicle) {
			System.out.println("Animal Tiredness  : " + ((AnimalPoweredVehicle) currentVehicle).getTiredness() + "%");
		}
		System.out.println("Segment type      :" + currentSegment.getClass().getSimpleName());
		System.out.println("Segment name      :" + currentSegment.getName());
		System.out.println("Trip Complete     : " + currentTrip.getPercentageComplete() + "%");
		System.out.println("---");
	}

}
