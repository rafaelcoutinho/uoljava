package edu.overtransport.exception;

/**
 * Defines the exception of running a vehicle and it does not have the resources
 * 
 * @author coutinho
 *
 */
public class LackOfResourcesException extends Exception {

	public LackOfResourcesException(String message) {
		super(message);
	}

}
