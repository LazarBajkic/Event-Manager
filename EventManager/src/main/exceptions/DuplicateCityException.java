package main.exceptions;

public class DuplicateCityException extends RuntimeException{

	public DuplicateCityException(String message) {
		super(message);
	}

}
