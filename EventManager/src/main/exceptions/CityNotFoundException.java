package main.exceptions;

public class CityNotFoundException extends RuntimeException{
	
	public CityNotFoundException(String message) {
		super(message);
	}
	
}
