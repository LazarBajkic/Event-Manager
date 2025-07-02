package main.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import main.model.Event;
import main.model.Participant;
import main.model.City;


public class EventManagerService {
	
	private Map<City,List<Event>> eventsInCities = new HashMap<>(); 
	private Map<Event,List<Participant>> participantsPerEvent = new HashMap<>();
	private List<City> cities = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	private List<Participant> participants = new ArrayList<>();
	
	public String addCity(String cityName) {
		
		if(cityName.isBlank()) {
			return "The city name cannot be empty";
		}
		
		cities.add(new City(cityName));
		return "City: "+cityName+" added successfully";
	}
	
	public String addEvent(String cityName,Event e) {
		
		if(cityName.isBlank() || cityName == null) {
			return "The city name cannot be empty";
		}
		
		if(findCityByName(cityName).get()==null) {
			return "The specified city is not in our records";
		}
			
		if(e==null) {
			return "The event you entered is not valid";
		}
		
		Optional<City> cityOpt = findCityByName(cityName);
	    if (cityOpt.isEmpty()) {
	        return "The specified city is not in our records";
	    }
		
		events.add(e);
		eventsInCities.computeIfAbsent(cityOpt.get(), g -> new ArrayList<>()).add(e);
		
		return "Event added to corresponding city";
	}
	
	public String addParticipant(String eventName,Participant p) {
		
		if(eventName.isBlank() || eventName == null) {
			return "The event name cannot be empty";
		}
		
		
		if(findEventByName(eventName).get()==null) {
			return "The specified event is not in our records";
		}
			
		if(p==null) {
			return "The participant you entered is not valid";
		}
		
		 Optional<Event> eventOpt = findEventByName(eventName);
		    if (eventOpt.isEmpty()) {
		        return "The specified event is not in our records";
		    }
		
		participants.add(p);
		participantsPerEvent.computeIfAbsent(eventOpt.get(), g -> new ArrayList<>()).add(p);
		
		return "Participant added to event";
	}
	
	public Optional<City> findCityByName(String cityName) {
		return cities.stream().filter(x->x.getIme().equals(cityName)).findAny(); 
	}
	
	public Optional<Event> findEventByName(String eventName) {
		return events.stream().filter(x->x.getName().equals(eventName)).findAny(); 
	}
	
}
