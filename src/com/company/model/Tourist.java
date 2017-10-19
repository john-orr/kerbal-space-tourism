package com.company.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tourist {

    private String name;
    private String location;
    Map<Integer, Flight> itinerary = new TreeMap<>();


    public Tourist(String name) {
        this.name = name.toUpperCase();
        this.location = "KERBIN";
    }

    public Tourist(String[] data) {
        this.name = data[1];
        this.location = data[2];
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

    public Map<Integer, Flight> getItinerary() {
        return itinerary;
    }

    public void setItinerary(Map<Integer, Flight> itinerary) {
        this.itinerary = itinerary;
    }

    public void addToItinerary(Flight flight) {
        addToItinerary(this.itinerary.values().size(), flight);
    }

    public void addToItinerary(Integer priority, Flight flight) {
        if (this.itinerary.containsKey(priority)) {
            System.out.println("Itinerary already contains flight of priority " + priority);
            return;
        }
        this.itinerary.put(priority, flight);
        flight.getCustomers().add(this);
    }

    public List<String> getFlightKeys() {
        List<String> flightKeys = new ArrayList<>();
        for (Flight flight : itinerary.values()) {
            flightKeys.add(flight.getKey());
        }
        return flightKeys;
    }
}
