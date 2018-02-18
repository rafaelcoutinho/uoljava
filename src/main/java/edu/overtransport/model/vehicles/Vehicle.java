package edu.overtransport.model.vehicles;

import edu.overtransport.exception.LackOfResourcesException;

public interface Vehicle {
	short getSpeed();

	void run() throws LackOfResourcesException;

	boolean accelerate();

	boolean brake();

}
