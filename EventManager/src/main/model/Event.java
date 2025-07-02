package main.model;

import java.util.Objects;

public class Event {
	
	private String name;
	private Type eventType;

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
