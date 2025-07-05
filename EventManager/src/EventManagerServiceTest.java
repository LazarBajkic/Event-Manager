import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.exceptions.CityNotFoundException;
import main.exceptions.DuplicateCityException;
import main.exceptions.DuplicateEventException;
import main.exceptions.EventNotFoundException;
import main.exceptions.NoEventsFoundException;
import main.exceptions.NullEventException;
import main.exceptions.NullParticipantException;
import main.model.City;
import main.model.Event;
import main.model.Participant;
import main.model.Type;
import main.service.EventManagerService;
import main.service.NoEventsInCityException;
import main.service.NoParticipantsFoundException;

class EventManagerServiceTest {

	EventManagerService ems;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		ems = new EventManagerService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddCityValid() {
		assertEquals("City: Kragujevac added successfully",ems.addCity("Kragujevac"));
	}
	
	@Test
	void testAddCityInvalid() {
		assertThrows(CityNotFoundException.class,() -> ems.addCity(""));
	}
	
	@Test
	void testAddDuplicateCity() {
	    ems.addCity("Novi Sad");
	    assertThrows(DuplicateCityException.class, () -> ems.addCity("Novi Sad"));
	}
	
	@Test
	void testAddEventValid() {
		ems.addCity("Novi Sad");
		assertEquals("Event added to corresponding city",ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING)));
	}
	
	@Test
	void testAddEventInvalidCity() {
		ems.addCity("Novi Sad");
		assertThrows(CityNotFoundException.class,() -> ems.addEvent(null, new Event("Wedding",Type.WEDDING)));
	}
	
	@Test
	void testAddEventInvalidEvent() {
		ems.addCity("Novi Sad");
		assertThrows(NullEventException.class,() -> ems.addEvent("Novi Sad", null));
	}
	
	@Test
	void testAddEventAlreadyExists() {
		ems.addCity("Novi Sad");
		ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING));
		assertThrows(DuplicateEventException.class,() -> ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING)));
	}


	@Test
	void testAddParticipantValid() {
		ems.addCity("Novi Sad");
		ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING));
		assertEquals("Participant added to event",ems.addParticipant("Wedding", new Participant("John",24,"John@gmail.com")));
	}
	
	@Test
	void testAddParticipantEventNotFound() {
		assertThrows(EventNotFoundException.class,()->ems.addParticipant("", new Participant("John",24,"John@gmail.com")));
	}
	
	@Test
	void testAddParticipantNull() {
		ems.addCity("Novi Sad");
		ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING));
		assertThrows(NullParticipantException.class,()->ems.addParticipant("Wedding", null));
	}
	
	@Test
	void testAddParticipantAlreadyRegistered() {
		ems.addCity("Novi Sad");
		ems.addEvent("Novi Sad", new Event("Wedding",Type.WEDDING));
		ems.addParticipant("Wedding", new Participant("John",24,"John@gmail.com"));
		assertThrows(NullParticipantException.class,()->ems.addParticipant("Wedding", new Participant("John",24,"John@gmail.com")));
	}

	@Test
	void testGetEventsByTypeValid() {
	    
	    City c1 = new City("Beograd");
	    City c2 = new City("Novi Sad");

	    Event e1 = new Event("Party", Type.PARTY);
	    Event e2 = new Event("Birthday", Type.BIRTHDAY);
	    
	    List<Event> events1 = List.of(e1);
	    List<Event> events2 = List.of(e2);
	    
	    ems.getEventsInCities().put(c1, events1);
	    ems.getEventsInCities().put(c2, events2);

	    assertDoesNotThrow(() -> ems.getEventsByType());
	}
	
	@Test
	void testGetEventsByTypeNoEvents() {
		 
		 City c1 = new City("Subotica");
		 ems.getEventsInCities().put(c1, new ArrayList<>());

		 assertThrows(NoEventsFoundException.class, () -> ems.getEventsByType());
	}

	@Test
	void testGetAverageParticipants_NoParticipants() {

	    Event e1 = new Event("Birthday", Type.BIRTHDAY);
	    ems.getParticipantsPerEvent().put(e1, new ArrayList<>()); 

	    assertThrows(NoParticipantsFoundException.class, () -> ems.getAverageParticipants());
	}
	
	@Test
	void testReturningParticipants_NoEventsInCity() {

	    City c1 = new City("Novi Sad");
	    ems.getEventsInCities().put(c1, new ArrayList<>()); 

	    assertThrows(NoEventsInCityException.class, () -> ems.returningParticipants());
	}
}
