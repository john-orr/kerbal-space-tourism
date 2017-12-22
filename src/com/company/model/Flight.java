package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Flight extends EntityWithNumericId implements Comparable<Flight> {

    private int id;
    private String origin;
    private String destination;
    private String flyby;
    private int capacity;
    private List<TouristItinerary> customerItineraries = new ArrayList<>();

    public Flight(String origin, String destination, String flyby, int capacity) {
        this.origin = origin.toUpperCase();
        this.destination = destination.toUpperCase();
        if (!flyby.isEmpty()) {
            this.flyby = flyby.toUpperCase();
        }
        this.capacity = capacity;
    }

    public Flight(int id, String origin, String destination, String flyby, int capacity) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.flyby = flyby;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlyby() {
        return flyby;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<TouristItinerary> getCustomerItineraries() {
        return customerItineraries;
    }

    List<TouristItinerary> getBookingsWithoutMissions(boolean includePrerequisites) {
        List<TouristItinerary> readyCustomerItineraries = new ArrayList<>();
        for (TouristItinerary customerItinerary : customerItineraries) {
            if (customerItinerary.getMission() == null
                    && (customerItinerary.getPrerequisite() == null || includePrerequisites)) {
                readyCustomerItineraries.add(customerItinerary);
            }
        }
        return readyCustomerItineraries;
    }

    public void addCustomerItinerary(TouristItinerary touristItinerary) {
        this.customerItineraries.add(touristItinerary);
    }

    public void removeCustomerItinerary(TouristItinerary touristItinerary) {
        this.customerItineraries.remove(touristItinerary);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Flight) {
            Flight that = (Flight) o;
            if (this.origin.equals(that.origin)
                    && this.destination.equals(that.destination)
                    && (this.flyby != null && this.flyby.equals(that.flyby)
                        || this.flyby == null && that.flyby == null)) {
                return true;
            }
        }
        return false;
    }

    @Override public int compareTo(Flight that) {
        if (!this.origin.equals(that.origin)) {
            return this.origin.compareTo(that.origin);
        }
        if (!this.destination.equals(that.destination)) {
            return this.destination.compareTo(that.destination);
        }
        if (this.flyby == null) {
            return -1;
        }
        if (that.flyby == null) {
            return 1;
        }
        return this.flyby.compareTo(that.flyby);
    }
}
