package edu.overtransport.model.vehicles;

import edu.overtransport.exception.NoFuelException;

/**
 * Combustion powered vehicles. Need gas and has a limited autonomy.
 * 
 * @author coutinho
 *
 */
public abstract class Car implements Vehicle {
	private int fuelTankPercentageFull = 0;
	private short currentSpeed = 0;
	private String name;

	protected abstract short getIncSpeedStep();

	protected abstract boolean isAtMaxSpeed();

	public Car(String name) {
		this.fuelTankPercentageFull = 100;

		if (name != "") {
			this.name = name;
		} else {
			this.name = "No Name";
		}

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

	/**
	 * @throws NoFuelException
	 *             - if car runs out of fuel
	 */
	@Override
	public void run() throws NoFuelException {
		if (fuelTankPercentageFull == 0) {
			throw new NoFuelException("Empty");
		}
		fuelTankPercentageFull -= 20;
	}

	public String getName() {
		return name;
	}
}
