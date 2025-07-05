package main.service;

public class NoEventsInCityException extends RuntimeException {
	
	public NoEventsInCityException(String message) {
		super(message);
	}
	
}
