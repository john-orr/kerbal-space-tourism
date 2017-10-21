package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Tourist extends Entity implements Comparable<Tourist> {

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

    public String getKey() {
        return getName();
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

    public List<TouristItinerary> getNonBlockingItineraries() {
        List<TouristItinerary> nonBlockingItineraries = new ArrayList<>();
        List<String> blockingKeys = new ArrayList<>();
        for (TouristItinerary touristItinerary : itinerary) {
            if (touristItinerary.getPrerequisite() != null) {
                blockingKeys.add(touristItinerary.getPrerequisite().getKey());
            }
        }
        for (TouristItinerary touristItinerary : itinerary) {
            if (!blockingKeys.contains(touristItinerary.getKey())) {
                nonBlockingItineraries.add(touristItinerary);
            }
        }
        return nonBlockingItineraries;
    }

    public TouristItinerary findItinerary(String itineraryKey) {
        for (TouristItinerary touristItinerary : itinerary) {
            if (touristItinerary.getKey().equals(itineraryKey)) {
                return touristItinerary;
            }
        }
        return null;
    }


    public void addToItinerary(TouristItinerary touristItinerary) {
        this.itinerary.add(touristItinerary);
    }

    public void removeFromItinerary(TouristItinerary touristItinerary, boolean patchPrerequisites) {
        this.itinerary.remove(touristItinerary);
        for (TouristItinerary remainingTouristItinerary : this.itinerary) {
            if (remainingTouristItinerary.getPrerequisite() != null
                    && remainingTouristItinerary.getPrerequisite().equals(touristItinerary)) {
                remainingTouristItinerary.setPrerequisite(patchPrerequisites ? touristItinerary.getPrerequisite() : null);
            }
        }
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

    @Override
    public int compareTo(Tourist that) {
        return this.name.compareTo(that.name);
    }
}
