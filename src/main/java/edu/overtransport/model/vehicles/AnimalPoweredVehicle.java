package edu.overtransport.model.vehicles;

import edu.overtransport.exception.ExhaustedAnimalException;

/**
 * Abstraction for a animal powered vehicle.
 * 
 * @author coutinho
 *
 */
public abstract class AnimalPoweredVehicle implements Vehicle {

	private int numberOfAnimals;
	private int energy = 100;

	public AnimalPoweredVehicle(int numberOfAnimals) {
		this.numberOfAnimals = numberOfAnimals;
	}

	public void feed() {
		energy = 100;
	}

	/**
	 * @throws ExhaustedAnimalException
	 *             - if animals were not fed
	 */
	@Override
	public void run() throws ExhaustedAnimalException {
		if (energy <= 0) {
			throw new ExhaustedAnimalException("Poor horse");
		}

		int tempEngergy = 10 * numberOfAnimals;
		if (energy - tempEngergy < 0) {
			energy = 0; // cannot be lower than 0.
		} else {
			energy -= 10 * numberOfAnimals;
		}
	}

	public int getTiredness() {
		return energy;
	}

	public int numberOfHorses() {
		return numberOfAnimals;
	}
}
