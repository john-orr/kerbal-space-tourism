package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Tourist {

    private String name;
    private String location;
    List<TouristItinerary> itinerary = new ArrayList<>();

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

    public List<TouristItinerary> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<TouristItinerary> itinerary) {
        this.itinerary = itinerary;
    }

    public void addToItinerary(Flight flight) {
        TouristItinerary touristItinerary = new TouristItinerary(this, flight, this.itinerary.size());
        addToItinerary(touristItinerary);
        flight.getCustomerItineraries().add(touristItinerary);
    }

    public void addToItinerary(TouristItinerary touristItinerary) {
        this.itinerary.add(touristItinerary);
    }

    public List<String> getFlightKeys() {
        List<String> flightKeys = new ArrayList<>();
        for (TouristItinerary touristItinerary : itinerary) {
            flightKeys.add(touristItinerary.getFlight().getKey());
        }
        return flightKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tourist) {
            Tourist that = (Tourist) o;
            return this.name.equals(that.name);
        }
        return false;
    }
}
