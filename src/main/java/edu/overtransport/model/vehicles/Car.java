package edu.overtransport.model.vehicles;

import edu.overtransport.exception.NoFuelException;

public abstract class Car implements Vehicle {
	private int fuelTankPercentageFull = 0;
	private short currentSpeed = 0;

	protected abstract short getIncSpeedStep();

	protected abstract boolean isAtMaxSpeed();

	public Car(String name) {
		this.fuelTankPercentageFull = 100;
	}

	public void refuel() {
		fuelTankPercentageFull = 100;
	}

	public int getFuelStatus() {
		return fuelTankPercentageFull;
	}

	@Override
	public boolean brake() {
		if (currentSpeed > 0) {
			currentSpeed -= 10;
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
		if (isAtMaxSpeed()) {
			return false;
		}
		currentSpeed += getIncSpeedStep();
		return true;
	}

	@Override
	public void run() throws NoFuelException {
		if (fuelTankPercentageFull == 0) {
			throw new NoFuelException();
		}
		fuelTankPercentageFull -= 20;
	}

}
