package com.company.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tourist {

    private String name;
    private String location;
    List<TouristItinerary> itinerary = new ArrayList<>();
    private boolean flagged;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        System.out.println(name + " location is now " + location);
        this.location = location;
    }

    public List<TouristItinerary> getItinerary() {
        return itinerary;
    }

    public TouristItinerary getLastItineraryItem() {
        return itinerary.get(itinerary.size() - 1);
    }

    public TouristItinerary getNextItineraryItem() {
        return itinerary.get(0);
    }

    public void addToItinerary(Flight flight) {
        TouristItinerary touristItinerary = new TouristItinerary(this, flight, this.itinerary.size());
        addToItinerary(touristItinerary);
        flight.getCustomerItineraries().add(touristItinerary);
    }

    public void addToItinerary(TouristItinerary touristItinerary) {
        this.itinerary.add(touristItinerary);
        Collections.sort(itinerary);
    }

    public void removeFromItinerary(TouristItinerary touristItinerary) {
        this.itinerary.remove(touristItinerary);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tourist) {
            Tourist that = (Tourist) o;
            return this.name.equals(that.name);
        }
        return false;
    }

    public void flagForDelete() {
        this.flagged = true;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void unflag() {
        this.flagged = false;
    }
}
