package edu.overtransport.model.vehicles;

public class RancingCar extends Car {
	private static final short MAX_RANCING_CAR_SPEED = 200;

	public RancingCar(String name) {
		super(name);
	}

	@Override
	protected boolean isAtMaxSpeed() {
		return getSpeed() == MAX_RANCING_CAR_SPEED;
	}

	@Override
	protected short getIncSpeedStep() {
		return 10;
	}

}
