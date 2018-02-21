package edu.overtransport.model.vehicles;

/**
 * A chariot is an animal powered vehicle that requires some horses to be
 * constructed.
 * 
 * It cannot accelerate more than MAX_CHARIOT_SPEED
 * 
 * @author coutinho
 *
 */
public class Chariot extends AnimalPoweredVehicle {
	private static final int MAX_CHARIOT_SPEED = 30;
	private short currentSpeed = 0;

	public Chariot(int numberOfAnimals) {
		super(numberOfAnimals);
		if (numberOfAnimals == 0) {
			throw new IllegalArgumentException("At least one horse is needed");
		} else if (numberOfAnimals > 10) {
			throw new IllegalArgumentException("Too many horses");

		}
	}

	@Override
	public boolean brake() {
		if (currentSpeed > 0) {
			currentSpeed -= 5;
			return true;
		}
		return false;
	}

	@Override
	public short getSpeed() {

		return currentSpeed;
	}

	@Override
	public boolean accelerate() {
		if (currentSpeed == MAX_CHARIOT_SPEED) {
			return false;
		}
		currentSpeed += 5;
		return true;
	}

}
