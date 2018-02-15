package edu.overtransport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.overtransport.model.Trip;
import edu.overtransport.model.road.CityStreet;
import edu.overtransport.model.road.Highway;
import edu.overtransport.model.road.RoadSegment;
import edu.overtransport.model.road.Track;

public class DestinationDB {
	public static final String TO_A_FARM = "TO_A_FARM";
	public static final String LONG_TRIP = "LONG_TRIP";
	public static final String SHORT_TRIP = "SHORT_TRIP";
	Map<String, Trip> reachableRegion = new HashMap<>();
	{
		List<RoadSegment> fullFromLondonPathToScotland = new ArrayList<>();
		fullFromLondonPathToScotland.add(new CityStreet("Strand St.", 50));
		fullFromLondonPathToScotland.add(new CityStreet("Fleet St.", 50));
		fullFromLondonPathToScotland.add(new Highway("A40", 120));
		fullFromLondonPathToScotland.add(new Highway("M5", 150));
		fullFromLondonPathToScotland.add(new Highway("M6", 170));
		fullFromLondonPathToScotland.add(new CityStreet("Carlops Rd.", 60));
		fullFromLondonPathToScotland.add(new CityStreet("Biggar.", 50));
		Trip longTrip = new Trip("Edinburg", fullFromLondonPathToScotland);

		List<RoadSegment> fullFromLondonPathToLiverpool = new ArrayList<>();
		fullFromLondonPathToLiverpool.add(new CityStreet("Strand St.", 40));
		fullFromLondonPathToLiverpool.add(new Highway("A40", 120));
		fullFromLondonPathToLiverpool.add(new Highway("M6", 170));
		fullFromLondonPathToLiverpool.add(new CityStreet("Bowring Park.", 40));
		Trip shortTrip = new Trip("Liverpool", fullFromLondonPathToLiverpool);

		List<RoadSegment> fullFromLondonPathToPrincessChristiansFarm = new ArrayList<>();
		fullFromLondonPathToPrincessChristiansFarm.add(new CityStreet("Wetminister Bridge.", 50));
		fullFromLondonPathToPrincessChristiansFarm.add(new Highway("A2", 120));
		fullFromLondonPathToPrincessChristiansFarm.add(new CityStreet("London Rd.", 60));
		fullFromLondonPathToPrincessChristiansFarm.add(new Track("Farm track"));
		Trip toPrincessChristianFarm = new Trip("Princess Christian's Farm",
				fullFromLondonPathToPrincessChristiansFarm);

		reachableRegion.put(SHORT_TRIP, shortTrip);
		reachableRegion.put(LONG_TRIP, longTrip);
		reachableRegion.put(TO_A_FARM, toPrincessChristianFarm);

	}

	public DestinationDB() {
	}

	public Trip getTrip(String string) {

		return reachableRegion.get(string);
	}

	public Collection<Trip> listTrips() {
		return reachableRegion.values();
	}

}
