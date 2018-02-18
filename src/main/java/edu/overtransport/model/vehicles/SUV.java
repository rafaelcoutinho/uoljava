package edu.overtransport.model.vehicles;

public class SUV extends Car {
	public SUV(String name) {
		super(name);
	}

	private static final short MAX_SUV_SPEED = 140;

	@Override
	protected boolean isAtMaxSpeed() {
		return getSpeed() == MAX_SUV_SPEED;
	}

	@Override
	protected short getIncSpeedStep() {
		return 20;
	}

}
