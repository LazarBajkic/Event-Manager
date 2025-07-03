package main.exceptions;

public class ParticipantAlreadyRegisteredException extends RuntimeException{

	public ParticipantAlreadyRegisteredException(String message) {
		super(message);
	}

}
