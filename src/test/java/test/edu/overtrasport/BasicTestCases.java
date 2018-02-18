package test.edu.overtrasport;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.overtransport.DestinationDB;
import edu.overtransport.TransportService;
import edu.overtransport.exception.BrokenCar;
import edu.overtransport.exception.LackOfResourcesException;
import edu.overtransport.exception.TicketingException;
import edu.overtransport.exception.UnsuitableVehicleException;
import edu.overtransport.model.Trip;
import edu.overtransport.model.vehicles.Chariot;
import edu.overtransport.model.vehicles.RacingCar;
import edu.overtransport.model.vehicles.OffRoad;

public class BasicTestCases {
	DestinationDB db = new DestinationDB();

	@Test
	public void testSlowSUVShortTrip() {
		OffRoad suv = new OffRoad("Jeep");
		TransportService ts = new TransportService();
		Trip toEdimburg = db.getTrip(DestinationDB.SHORT_TRIP);
		ts.startTrip(suv, toEdimburg);
		try {
			suv.accelerate();
			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				ts.printState();
			}
			ts.printState();
		} catch (TicketingException e) {
			e.printStackTrace();
			fail("SUV is slow enough");
		} catch (UnsuitableVehicleException e) {
			e.printStackTrace();
			fail("SUV is good for this trip");
		} catch (LackOfResourcesException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFastSUVShortTrip() {
		OffRoad suv = new OffRoad("Jeep");
		TransportService ts = new TransportService();
		Trip toEdimburg = db.getTrip(DestinationDB.SHORT_TRIP);
		ts.startTrip(suv, toEdimburg);
		try {
			while (suv.accelerate())
				;
			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				ts.printState();
			}
			ts.printState();
			fail("SUV is too fast, should have got a ticket");
		} catch (TicketingException e) {

		} catch (UnsuitableVehicleException e) {
			fail("SUV is good for this trip");
		} catch (LackOfResourcesException e) {
			e.printStackTrace();
			fail("Vehicle has autonomy");
		}
	}

	@Test
	public void testRacingCarToAFarm() {
		RacingCar vehicle = new RacingCar("Lamborghini");
		TransportService ts = new TransportService();
		Trip toEdimburg = db.getTrip(DestinationDB.TO_A_FARM);
		ts.startTrip(vehicle, toEdimburg);
		try {
			vehicle.accelerate();
			;
			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				ts.printState();
			}
			ts.printState();
			fail("Racing car is not suitable");
		} catch (TicketingException e) {
			fail("Racing car is not too fast");
		} catch (UnsuitableVehicleException e) {

		} catch (LackOfResourcesException e) {
			e.printStackTrace();
			fail("Vehicle has autonomy");
		}
	}

	@Test
	public void testRacingCarToAFarmTooFastForATrack() {
		RacingCar vehicle = new RacingCar("Lamborghini");
		TransportService ts = new TransportService();
		Trip trip = db.getTrip(DestinationDB.TO_A_FARM);
		ts.startTrip(vehicle, trip);
		try {
			while (vehicle.getSpeed() < 40) {
				vehicle.accelerate();
			}

			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				ts.printState();
			}
			ts.printState();
			fail("Racing car is too fast and may break on entering a Track");
		} catch (TicketingException e) {
			fail("Racing car is not too fast for getting a ticket");
		} catch (BrokenCar e) {
			// ("Racing car is too fast and may break on entering a Track");
		} catch (UnsuitableVehicleException e) {
			fail("Racing car is not too fast");
		} catch (LackOfResourcesException e) {
			e.printStackTrace();
			fail("Vehicle has autonomy");
		}
	}

	@Test
	public void testRacingCarLongTripWithoutRefueling() {
		RacingCar vehicle = new RacingCar("Lamborghini");
		TransportService ts = new TransportService();
		Trip toEdimburg = db.getTrip(DestinationDB.LONG_TRIP);
		ts.startTrip(vehicle, toEdimburg);
		try {
			while (vehicle.getSpeed() < 40) {
				vehicle.accelerate();
			}

			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				ts.printState();
			}
			ts.printState();
			fail("Racing car must re fuel before completing long trip");
		} catch (TicketingException e) {
			fail("Racing car is not too fast for getting a ticket: " + e.getMessage());
		} catch (BrokenCar e) {
			fail("Racing car should not break");
		} catch (UnsuitableVehicleException e) {
			fail("Racing car is not too fast");
		} catch (LackOfResourcesException e) {
		}
	}

	@Test
	public void testChariotLongTripWithoutRefueling() {
		Chariot vehicle = new Chariot(1);
		TransportService ts = new TransportService();

		Trip toEdimburg = db.getTrip(DestinationDB.LONG_TRIP);
		ts.startTrip(vehicle, toEdimburg);
		try {
			vehicle.accelerate();

			while (ts.hasMoreSegments()) {
				ts.driveSegment();

				ts.printState();
			}
			ts.printState();
			fail("Racing car must re fuel before completing long trip");
		} catch (TicketingException e) {
			fail("Racing car is not too fast for getting a ticket: " + e.getMessage());
		} catch (BrokenCar e) {
			fail("Racing car should not break");
		} catch (UnsuitableVehicleException e) {
			fail("Racing car is not too fast");
		} catch (LackOfResourcesException e) {
		}
	}

	@Test
	public void testChariotLongTripRefueling() {
		Chariot vehicle = new Chariot(1);
		TransportService ts = new TransportService();
		Trip toEdimburg = db.getTrip(DestinationDB.LONG_TRIP);
		ts.startTrip(vehicle, toEdimburg);
		try {
			vehicle.accelerate();

			while (ts.hasMoreSegments()) {
				ts.driveSegment();
				vehicle.feed();
				ts.printState();
			}
			ts.printState();

		} catch (TicketingException e) {
			fail("Chariot is not too fast for getting a ticket: " + e.getMessage());
		} catch (BrokenCar e) {
			fail("Chariot should not break");
		} catch (UnsuitableVehicleException e) {
			fail("Chariot is not too fast");
		} catch (LackOfResourcesException e) {
			fail("Chariot is fed constantly");
		}
	}

}
