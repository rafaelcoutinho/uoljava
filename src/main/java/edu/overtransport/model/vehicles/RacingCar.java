package edu.overtransport.model.vehicles;

/**
 * A fast car and sportive. Can achieve very high speeds.
 * 
 * @author coutinho
 *
 */
public class RacingCar extends Car {
	private static final short MAX_RANCING_CAR_SPEED = 200;

	public RacingCar(String name) {
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
