package main.service;

import java.util.Map;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import main.model.Event;
import main.model.Participant;
import main.exceptions.CityNotFoundException;
import main.exceptions.DuplicateCityException;
import main.exceptions.EventNotFoundException;
import main.exceptions.NoEventsFoundException;
import main.exceptions.NullEventException;
import main.exceptions.NullParticipantException;
import main.model.City;
import main.model.Type;



public class EventManagerService {
	
	private Map<City,List<Event>> eventsInCities = new HashMap<>(); 
	private Map<Event,List<Participant>> participantsPerEvent = new HashMap<>();
	private List<City> cities = new ArrayList<>();
	private List<Event> events = new ArrayList<>();
	private List<Participant> participants = new ArrayList<>();
	
	public String addCity(String cityName) {
		
		if(cityName.isBlank()) {
			throw new CityNotFoundException("City name cannot be blank.");
		}
		
		if(findCityByName(cityName).isPresent()) {
			throw new DuplicateCityException("The city "+cityName+" is already registered");
		}
		
		cities.add(new City(cityName));
		return "City: "+cityName+" added successfully";
	}
	
	public String addEvent(String cityName,Event e) {
		
		if(cityName == null || cityName.isBlank()) {
			throw new CityNotFoundException("City name cannot be blank.");
		}
			
		if(e==null) {
			throw new NullEventException("Event cannot be null");
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
		
		if(eventName == null || eventName.isBlank()) {
			throw new EventNotFoundException("The event name cannot be blank.");
		}
			
		if(p==null) {
			throw new NullParticipantException("Participant cannot be null");
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
		return cities.stream().filter(x->x.getName().equals(cityName)).findAny(); 
	}
	
	public Optional<Event> findEventByName(String eventName) {
		return events.stream().filter(x->x.getName().equals(eventName)).findAny(); 
	}
	
	public void getEventsByType() {
		
		 if (eventsInCities.isEmpty() || 
			        eventsInCities.values().stream().allMatch(List::isEmpty)) {
			        throw new NoEventsFoundException("No events were found in any city.");
			    }
		
		Map<Type,List<String>> eventsByType = eventsInCities.values().stream()
				.flatMap(List::stream)
				.collect(Collectors.groupingBy(Event::getEventType,
						 Collectors.mapping(Event::toString, Collectors.toList())));
		
		eventsByType.forEach((k,v) -> System.out.println(k + " "+ v));
	}
	
	public void getAverageParticipants() {
		
		if (participantsPerEvent.isEmpty() || 
		        participantsPerEvent.values().stream().allMatch(List::isEmpty)) {
		        throw new NoParticipantsFoundException("No participants registered for any event.");
		    }
		
		Map<Type,Double> averageParticipantsMap = participantsPerEvent.entrySet().stream()
				.collect(Collectors.groupingBy(e -> e.getKey()
						.getEventType(),Collectors.averagingInt(e -> e.getValue().size())));
		
		averageParticipantsMap.forEach((k,v) -> System.out.println(k + " "+ v));
	}
	
	public void returningParticipants() {
		eventsInCities.entrySet().stream().map(entry -> {
	        City grad = entry.getKey();
	        List<Event> events = entry.getValue();
	        
	        
	        Map<Participant, Long> numberOfAppearences = events.stream()
	            .flatMap(e -> participantsPerEvent.getOrDefault(e, List.of()).stream())
	            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

	        Set<Participant> visePuta = numberOfAppearences.entrySet().stream()
	            .filter(e -> e.getValue() > 1)
	            .map(Map.Entry::getKey)
	            .collect(Collectors.toSet());

	        return Map.entry(grad, visePuta);
	    }).filter(e -> !e.getValue().isEmpty())
		.forEach(e -> {
		    System.out.println("City: " + e.getKey().getName());
		    System.out.println("Returning participants: ");
		    e.getValue().forEach(System.out::println);
		});
	}
	
}
