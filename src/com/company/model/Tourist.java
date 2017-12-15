package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Tourist implements Comparable<Tourist> {

    private String name;
    private String location;
    private List<TouristItinerary> itinerary = new ArrayList<>();
    private boolean flagged;

    public Tourist(String name) {
        this.name = name.toUpperCase();
        this.location = "KERBIN";
    }

    public Tourist(String name, String location) {
        this.name = name;
        this.location = location;
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

    public TouristItinerary getMostRecentItinerary() {
        return itinerary.get(itinerary.size() - 1);
    }

    public List<TouristItinerary> getNonBlockingItineraries() {
        List<TouristItinerary> nonBlockingItineraries = new ArrayList<>();
        List<Integer> blockingIds = new ArrayList<>();
        for (TouristItinerary touristItinerary : itinerary) {
            if (touristItinerary.getPrerequisite() != null) {
                blockingIds.add(touristItinerary.getPrerequisite().getId());
            }
        }
        for (TouristItinerary touristItinerary : itinerary) {
            if (!blockingIds.contains(touristItinerary.getId())) {
                nonBlockingItineraries.add(touristItinerary);
            }
        }
        return nonBlockingItineraries;
    }

    public TouristItinerary findItinerary(int itineraryId) {
        for (TouristItinerary touristItinerary : itinerary) {
            if (touristItinerary.getId() == itineraryId) {
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

    void flagForDelete() {
        this.flagged = true;
    }

    boolean isFlagged() {
        return flagged;
    }

    public void unFlag() {
        this.flagged = false;
    }

    @Override
    public int compareTo(Tourist that) {
        return this.name.compareTo(that.name);
    }
}
