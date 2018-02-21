package edu.overtransport.exception;

/**
 * Exception subclass of LackOfResourcesException specific to Car. Defines the
 * event of car getting out of gas enough.
 * 
 * @author coutinho
 *
 */
public class NoFuelException extends LackOfResourcesException {

	public NoFuelException(String message) {
		super(message);
	}

}
