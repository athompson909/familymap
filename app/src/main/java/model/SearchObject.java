package model;

/**
 * Created by athom909 on 4/12/16.
 * Person and Event both inherit this so I can use them for the same list
 */
public class SearchObject {

    public enum Type { PERSON, EVENT }

    public Type type;

    SearchObject(String type) {
        switch (type) {
            case "person": this.type = Type.PERSON; break;
            case "event": this.type = Type.EVENT; break;
            default: assert false;
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
