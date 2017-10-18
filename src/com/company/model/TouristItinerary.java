package com.company.model;

public class TouristItinerary {

    private String tourist;
    private String flight;

    public TouristItinerary(String[] data) {
        this.tourist = data[1];
        this.flight = data[2];
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
}
