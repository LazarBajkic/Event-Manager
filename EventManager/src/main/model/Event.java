package main.model;

import java.util.List;
import java.util.Objects;

public class Event {
	
	private String name;
	private Type eventType;
	private List<Participant> participants;
	

	public Event(String name,Type eventType) {
		super();
		this.name=name;
		this.eventType=eventType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getEventType() {
		return eventType;
	}

	public void setEventType(Type eventType) {
		this.eventType = eventType;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return eventType == other.eventType
				&& Objects.equals(name, other.name);
	}

	
	
	
	
}
