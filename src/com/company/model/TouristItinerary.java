package com.company.model;

public class TouristItinerary {

    private String tourist;
    private String flight;
    private int priority;

    public TouristItinerary(String[] data) {
        this.tourist = data[1];
        this.flight = data[2];
        this.priority = Integer.parseInt(data[3]);
    }

    public String getTourist() {
        return tourist;
    }

    public void setTourist(String tourist) {
        this.tourist = tourist;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
