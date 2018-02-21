package edu.overtransport.exception;

/**
 * Exception subclass of LackOfResourcesException specific to
 * AnimalPoweredVehicle. Defines the event of animals not having being fed
 * enough.
 * 
 * @author coutinho
 *
 */
public class ExhaustedAnimalException extends LackOfResourcesException {

	public ExhaustedAnimalException(String message) {
		super(message);
	}

}
