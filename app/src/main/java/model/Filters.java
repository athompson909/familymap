package model;

/**
 * Created by athom909 on 4/8/16.
 */
public class Filters {
    private static Filters instance = new Filters();

    public static Filters getInstance() {
        return instance;
    }

    private Filters() {
        baptismEventsOn = true;
        birthEventsOn = true;
        censusEventsOn = true;
        christeningEventsOn = true;
        deathEventsOn = true;
        marriageEventsOn = true;
        fathersSideOn = true;
        mothersSideOn = true;
        maleEventsOn = true;
        femaleEventsOn = true;
    }

    private boolean baptismEventsOn;
    private boolean birthEventsOn;
    private boolean censusEventsOn;
    private boolean christeningEventsOn;
    private boolean deathEventsOn;
    private boolean marriageEventsOn;
    private boolean fathersSideOn;
    private boolean mothersSideOn;
    private boolean maleEventsOn;
    private boolean femaleEventsOn;

    public boolean isEventOn(Event.EventType eventType) {
        switch (eventType) {
            case BAPTISM: return isBaptismEventsOn();
            case BIRTH: return isBirthEventsOn();
            case CHRISTENING: return isChristeningEventsOn();
            case DEATH: return isDeathEventsOn();
            case MARRIAGE: return isMarriageEventsOn();
            default: return true;
        }
    }

    public boolean isGenderOn(Person.Gender gender) {
        switch (gender) {
            case M: return isMaleEventsOn();
            case F: return isFemaleEventsOn();
            default: assert false;
        }
        return false;
    }

    public boolean isBaptismEventsOn() {
        return baptismEventsOn;
    }

    public void setBaptismEventsOn(boolean baptismEventsOn) {
        this.baptismEventsOn = baptismEventsOn;
    }

    public boolean isBirthEventsOn() {
        return birthEventsOn;
    }

    public void setBirthEventsOn(boolean birthEventsOn) {
        this.birthEventsOn = birthEventsOn;
    }

    public boolean isCensusEventsOn() {
        return censusEventsOn;
    }

    public void setCensusEventsOn(boolean censusEventsOn) {
        this.censusEventsOn = censusEventsOn;
    }

    public boolean isChristeningEventsOn() {
        return christeningEventsOn;
    }

    public void setChristeningEventsOn(boolean christeningEventsOn) {
        this.christeningEventsOn = christeningEventsOn;
    }

    public boolean isDeathEventsOn() {
        return deathEventsOn;
    }

    public void setDeathEventsOn(boolean deathEventsOn) {
        this.deathEventsOn = deathEventsOn;
    }

    public boolean isMarriageEventsOn() {
        return marriageEventsOn;
    }

    public void setMarriageEventsOn(boolean marriageEventsOn) {
        this.marriageEventsOn = marriageEventsOn;
    }

    public boolean isFathersSideOn() {
        return fathersSideOn;
    }

    public void setFathersSideOn(boolean fathersSideOn) {
        this.fathersSideOn = fathersSideOn;
    }

    public boolean isMothersSideOn() {
        return mothersSideOn;
    }

    public void setMothersSideOn(boolean mothersSideOn) {
        this.mothersSideOn = mothersSideOn;
    }

    public boolean isMaleEventsOn() {
        return maleEventsOn;
    }

    public void setMaleEventsOn(boolean maleEventsOn) {
        this.maleEventsOn = maleEventsOn;
    }

    public boolean isFemaleEventsOn() {
        return femaleEventsOn;
    }

    public void setFemaleEventsOn(boolean femaleEventsOn) {
        this.femaleEventsOn = femaleEventsOn;
    }
}