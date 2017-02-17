package model;

import java.util.List;

/**
 * Created by athom909 on 4/9/16.
 */
public class SearchResults {
    private static SearchResults instance = new SearchResults();

    public static SearchResults getInstance() {
        return instance;
    }

    private SearchResults() {
    }

    List<Person> people;
    List<Event> events;

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
