package main.exceptions;

public class DuplicateEventException extends RuntimeException{

	public DuplicateEventException(String message) {
		super(message);
	}

}
