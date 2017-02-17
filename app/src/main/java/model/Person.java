package model;

import com.amazon.geo.mapsv2.model.LatLng;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by athom909 on 3/22/16.
 */
public class Person extends SearchObject {

    public enum Gender { M, F }

    // in the order it is in the JSON
    private String descendant;
    private String personId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String father = null;
    private String mother = null;
    private String spouse = null;

    // and all other variables:
    private String child = null;
    private String fullName;

    // for FamilyTree
    private Object address;

    private TreeMap<Integer, Event> personEvents;
    private List<LatLng> personEventCoordinates;

    /**
     * just pass in a jsonObject and that's all you need!
     * @param data
     */
    public Person(JSONObject data) {
        super("person");
        try {
            descendant = data.getString("descendant");
            personId = data.getString("personID");
            firstName = data.getString("firstName");
            lastName = data.getString("lastName");
            fullName = firstName + " " + lastName;
            setGender(data.getString("gender"));
            if(data.has("father"))
                setFather(data.getString("father"));
            if(data.has("mother"))
                setMother(data.getString("mother"));
            if(data.has("spouse"))
                setSpouse(data.getString("spouse"));
            personEvents = new TreeMap<>();
            personEventCoordinates = new ArrayList<>();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // *** GETTERS AND SETTERS: ***

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(String gender) {
        switch (gender) {
            case "m":
                this.gender = Gender.M;
                break;
            case "f":
                this.gender = Gender.F;
                break;
            default:
                assert false;
        }
    }

    public String getGenderAsString() {
        switch (gender) {
            case M:
                return new String("Male");
            case F:
                return new String("Female");
            default:
                assert false;
        }
        return null;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public Map<Integer, Event> getPersonEvents() {
        return personEvents;
    }

    public void setPersonEvents(TreeMap<Integer, Event> personEvents) {
        this.personEvents = personEvents;
    }

    public void addToPersonEvents(Event e) {
        personEvents.put(e.getYear(), e);
    }

    public LatLng getFirstEventCoordinates() {
        for(TreeMap.Entry<Integer, Event> entry : personEvents.entrySet()) {
            return entry.getValue().getCoordinates();
        }
        return null;
    }

    /** it looks like this might not be working 100% of the time
     * @return
     */
    public String getFirstEventId() {
        for(TreeMap.Entry<Integer, Event> entry : personEvents.entrySet()) {
            return entry.getValue().getEventId();
        }
        return null;
    }

    public List<LatLng> getPersonEventCoordinates() {
        List<LatLng> list = new ArrayList<>();
        for(TreeMap.Entry<Integer, Event> entry : personEvents.entrySet()) {
            if(Filters.getInstance().isEventOn(entry.getValue().getDescription())) {
                list.add(entry.getValue().getCoordinates());
            }
        }
        return list;
    }

    public List<Event> getPersonEventsInOrder() {
        List<Event> events = new ArrayList<>();
        for(TreeMap.Entry<Integer, Event> entry : personEvents.entrySet()) {
            events.add(entry.getValue());
        }
        return getPersonEventsInOrder();
    }

    public void setPersonEventCoordinates() {
        for(TreeMap.Entry<Integer, Event> entry : personEvents.entrySet()) {
            personEventCoordinates.add(entry.getValue().getCoordinates());
        }
    }

    /**
     * This will occur when the personEvents is iterated through
     * ...never called
     * @param coord
     */
    public void addToPersonEventCoordinates(LatLng coord) {
        personEventCoordinates.add(coord);
    }
}