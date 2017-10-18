package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Tourist {

    private String name;
    private String location;
    List<Flight> itinerary;

    public Tourist(String name) {
        this.name = name.toUpperCase();
        this.location = "KERBIN";
        this.itinerary = new ArrayList<>();
    }

    public Tourist(String[] data) {
        this.name = data[1];
        this.location = data[2];
        this.itinerary = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Flight> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<Flight> itinerary) {
        this.itinerary = itinerary;
    }

    public void addToItinerary(Flight flight) {
        this.itinerary.add(flight);
        flight.getCustomers().add(this);
    }

    public List<String> getFlightKeys() {
        List<String> flightKeys = new ArrayList<>();
        for (Flight flight : itinerary) {
            flightKeys.add(flight.getKey());
        }
        return flightKeys;
    }
}
