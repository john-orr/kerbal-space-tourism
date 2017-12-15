package com.company.model;

public class Vessel {

    private String name;
    private String location;
    private int capacity;

    public Vessel(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location != null) {
            System.out.println(name + " location is now " + location);
        } else {
            System.out.println(name + " has departed");
        }
        this.location = location;
    }

    public void readyForDeparture() {
        this.location = this.location + "_DEP";
    }

    public int getCapacity() {
        return capacity;
    }

}
