package com.company.model;

public class Vessel extends Entity {

    private String name;
    private String location;
    private int capacity;

    public Vessel(String[] data) {
        this.name = data[1];
        if (!data[2].equals("null")) {
            this.location = data[2];
        }
        this.capacity = Integer.parseInt(data[3]);
    }

    public Vessel(String vesselName, String location, int capacity) {
        this.name = vesselName.toUpperCase();
        this.location = location;
        this.capacity = capacity;
    }

    @Override String getKey() {
        return getName();
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
