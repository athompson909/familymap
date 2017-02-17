package model;

import android.util.Log;

import com.amazon.geo.mapsv2.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by athom909 on 3/22/16.
 */
public class UserInfo {
    private static UserInfo instance = new UserInfo();

    public static UserInfo getInstance() {
        return instance;
    }

    public static void clear() {
        instance = new UserInfo();
    }

    private static Person currentPerson = null;
    private static String selectedEventId = null;

    public boolean isUserSpouse() {
        if (currentPerson == null) return false;
        else return (currentPerson.getPersonId().equals(user.getSpouse()));
    }

    private Map<String, Person> people;
    private Map<String, Event> events;
    private Map<String, List<Event>> personEvents;// maybe get rid of this too...

    private Person user;

    private FamilyTree familyTree;

    private UserInfo() {
        people = new HashMap<>();
        events = new HashMap<>();
        personEvents = new HashMap<>();
        user = null;

        currentPerson = null;
        selectedEventId = null;
    }

    public Person getPerson(String personId) {
        return people.get(personId);
    }

    public Event getEvent(String eventId) {
        return events.get(eventId);
    }

    public List<Event> getPersonsEvents(String personId) {
        return personEvents.get(personId);
    }

    public Map<String, Person> getPeople() {
        return people;
    }

    public void setPeople(Map<String, Person> people) {
        this.people = people;
    }

    public void addPeople(Person person) {
        people.put(person.getPersonId(), person);
    }

    public Map<String, Event> getEvents() {
        return events;
    }

    public void setEvents(Map<String, Event> events) {
        this.events = events;
    }

    public void addEvents(Event event) {
        events.put(event.getEventId(), event);
    }

    public Map<String, List<Event>> getPersonEvents() {
        return personEvents;
    }

    public void setUserEvents() {
        List<Event> l = personEvents.get(user.getPersonId());
        for(Event e : l) {
            user.addToPersonEvents(e);
        }
    }

    public void setPersonEvents(Map<String, List<Event>> personEvents) {
        this.personEvents = personEvents;
    }

    public void addPersonEvents(Event event) {
        if(personEvents.get(event.getPersonId()) == null) {
            List list = new ArrayList<>();
            list.add(event);
            personEvents.put(event.getPersonId(), list);
        }
        else {
            personEvents.get(event.getPersonId()).add(event);
        }
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public FamilyTree getFamilyTree() {
        return familyTree;
    }

    public void setFamilyTree() {
        familyTree = new FamilyTree(user);
    }

    public void setFamilyTree(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public static Person getCurrentPerson() {
        return currentPerson;
    }

    public static void setCurrentPerson(Person currentPerson) {
        UserInfo.currentPerson = currentPerson;
    }

    public static String getSelectedEventId() {
        return selectedEventId;
    }

    public static void setSelectedEventId(String selectedEventId) {
        UserInfo.selectedEventId = selectedEventId;
    }

    public void setPeoplesEventCoordinateLists() {
        for(HashMap.Entry<String, Person> entry : people.entrySet()) {
            entry.getValue().setPersonEventCoordinates();
        }
    }

    public static Person.Gender oppositeGender(Person.Gender g) {
        switch (g) {
            case M: return Person.Gender.F;
            case F: return Person.Gender.M;
            default: assert false;
        }
        return null;
    }
}