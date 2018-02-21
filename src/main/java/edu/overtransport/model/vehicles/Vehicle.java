package edu.overtransport.model.vehicles;

import edu.overtransport.exception.LackOfResourcesException;

/**
 * Vehicle interface contract, making easier for a controller to handle any
 * class that implements this interface
 * 
 * @author coutinho
 *
 */

public interface Vehicle {

	/**
	 * Vehicle runs for some km.
	 * 
	 * @throws LackOfResourcesException
	 *             - if vehicle gets out of resources
	 */
	void run() throws LackOfResourcesException;

	/**
	 * increase vehicle speed
	 * 
	 * @return true/false if it was able to increase the speed
	 */

	boolean accelerate();

	/**
	 * decrease vehicle speed
	 * 
	 * @return true/false if it was able to reduce the speed
	 */
	boolean brake();

	/**
	 * Returns the current speed.
	 * 
	 * @return speed
	 */
	short getSpeed();
}
