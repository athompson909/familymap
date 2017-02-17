package model;

import com.amazon.geo.mapsv2.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by athom909 on 4/2/16.
 */
public class FamilyTree {

    private Node root; // even if this isn't used, i still think it's important for the constructor
    private Node rootSpouse;

    private List<LatLng> firstLineFather;
    private List<LatLng> firstLineMother;

    public FamilyTree(Person user) {
        if(user != null)
            root = new Node(user);
        else
            root = null;
    }

    public List<LatLng> getFatherLine(String eventId, Person p) {
        if(!Filters.getInstance().isGenderOn(Person.Gender.M)) return new ArrayList<>();
        List<LatLng> fatherLine = new ArrayList<>();

        UserInfo inst = UserInfo.getInstance();
        if(Filters.getInstance().isEventOn(inst.getEvent(eventId).getDescription())) {
            fatherLine.add(new LatLng(inst.getEvent(eventId).getLatitude(),
                    inst.getEvent(eventId).getLongitude()));
        }

        Node n = (Node) p.getAddress();

        firstLineFather = new ArrayList<>();
        if(n.getFather() != null) {
            firstLineFather.add(new LatLng(inst.getEvent(eventId).getLatitude(),
                    inst.getEvent(eventId).getLongitude()));
            firstLineFather.add(n.getFather().getPerson().getFirstEventCoordinates());
        }


        n.getFatherLine(fatherLine);

        return fatherLine;
    }

    public List<LatLng> getMotherLine(String eventId, Person p) {
        if(!Filters.getInstance().isGenderOn(Person.Gender.F)) return new ArrayList<>();
        List<LatLng> motherLine = new ArrayList<>();

        UserInfo inst = UserInfo.getInstance();
        if(Filters.getInstance().isEventOn(inst.getEvent(eventId).getDescription())) {
            motherLine.add(new LatLng(inst.getEvent(eventId).getLatitude(),
                    inst.getEvent(eventId).getLongitude()));
        }

        Node n = (Node) p.getAddress();

        firstLineMother = new ArrayList<>();
        if(n.getMother() != null) {
            firstLineMother.add(new LatLng(inst.getEvent(eventId).getLatitude(),
                    inst.getEvent(eventId).getLongitude()));
            firstLineMother.add(n.getMother().getPerson().getFirstEventCoordinates());
        }

        n.getMotherLine(motherLine);

        return motherLine;
    }

    public List<Person> getPaternalAncestors() {
        return root.getAncestors(root.getFather());
    }

    public List<Person> getMaternalAncestors() {
        return root.getAncestors(root.getMother());
    }

    private class Node {

        public Node(Person person) {
            if(person != null) {
                UserInfo inst = UserInfo.getInstance();
                this.person = person;
                // for some reason this worked over this.person.setAddress(this);
                inst.getPerson(person.getPersonId()).setAddress(this);
                if(person.getFather() != null) {
                    inst.getPerson(this.person.getFather()).setChild(this.person.getPersonId());
                    father = new Node(inst.getPerson(person.getFather()));
                }
                if(person.getMother() != null) {
                    inst.getPerson(this.person.getMother()).setChild(this.person.getPersonId());
                    mother = new Node(inst.getPerson(person.getMother()));

                }
            }
        }

        private Person person;

        private Node father;
        private Node mother;

        public List<Person> getAncestors(Node n) {
            List<Person> l = new ArrayList<>();

            addAncestors(n, l);

            return l;
        }

        public void addAncestors(Node n, List<Person> l) {
            if(n != null) {
                l.add(n.getPerson());
                addAncestors(n.getFather(), l);
                addAncestors(n.getMother(), l);
            }
        }

        public void getFatherLine(List<LatLng> fatherLine) {
            if(!Filters.getInstance().isGenderOn(Person.Gender.M)) return;
            else if(father != null) {
                fatherLine.add(father.getPerson().getFirstEventCoordinates());
                father.getFatherLine(fatherLine);
            }
        }

        public void getMotherLine(List<LatLng> motherLine) {
            if(!Filters.getInstance().isGenderOn(Person.Gender.F)) return;
            else if(mother != null) {
                motherLine.add(mother.getPerson().getFirstEventCoordinates());
                mother.getMotherLine(motherLine);
            }
        }


        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Node getFather() {
            return father;
        }

        public void setFather(Node father) {
            this.father = father;
        }

        public Node getMother() {
            return mother;
        }

        public void setMother(Node mother) {
            this.mother = mother;
        }
    }


    public List<LatLng> getFirstLineFather() {
        return firstLineFather;
    }

    public void setFirstLineFather(List<LatLng> firstLineFather) {
        this.firstLineFather = firstLineFather;
    }

    public List<LatLng> getFirstLineMother() {
        return firstLineMother;
    }

    public void setFirstLineMother(List<LatLng> firstLineMother) {
        this.firstLineMother = firstLineMother;
    }
}
