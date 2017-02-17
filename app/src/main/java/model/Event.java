package model;

import com.amazon.geo.mapsv2.model.LatLng;

import org.json.JSONObject;

/**
 * Created by athom909 on 3/22/16.
 */
public class Event extends SearchObject {

    public enum EventType { BAPTISM, BIRTH, CENSUS, CHRISTENING, DEATH, MARRIAGE, OTHER }

    private String eventId;
    private String personId;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventTitle;
    private EventType description;
    private int year;
    private String descendant;

    private LatLng coordinates;
    private String subtitle;

    public Event(JSONObject data) {
        super("event");
        try {
            eventId = data.getString("eventID");
            personId = data.getString("personID");
            latitude = data.getDouble("latitude");
            longitude = data.getDouble("longitude");
            coordinates = new LatLng(latitude, longitude);
            country = data.getString("country");
            city = data.getString("city");
            eventTitle = data.getString("description");
            setDescription(eventTitle);
            year = data.getInt("year");
            descendant = data.getString("descendant");
            subtitle = eventTitle + ": " + country + ", " + city + " (" + year + ")";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EventType getDescription() {
        return description;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setDescription(EventType description) {
        this.description = description;
    }

    public void setDescription(String description) {
        switch (description) {
            case "birth":
                this.description = EventType.BIRTH;
                break;
            case "christening":
                this.description = EventType.CHRISTENING;
                break;
            case "baptism":
                this.description = EventType.BAPTISM;
                break;
            case "census":
                this.description = EventType.CENSUS;
                break;
            case "marriage":
                this.description = EventType.MARRIAGE;
                break;
            case "death":
                this.description = EventType.DEATH;
                break;
            default:
                this.description = EventType.OTHER;
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
