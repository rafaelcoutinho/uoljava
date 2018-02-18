package edu.overtransport.model.vehicles;

public class Chariot extends AnimalPoweredVehicle {
	private short currentSpeed = 0;

	public Chariot(int numberOfAnimals) {
		super(numberOfAnimals);
		if (numberOfAnimals == 0) {
			throw new IllegalArgumentException("At least one horse is needed");
		}else if (numberOfAnimals>10) {
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
		if (currentSpeed == 30) {
			return false;
		}
		currentSpeed += 5;
		return true;
	}

}
