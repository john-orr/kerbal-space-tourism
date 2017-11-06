package com.company.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mission extends Entity implements Comparable<Mission> {

    private String key;
    private Flight flight;
    private Vessel vessel;
    private String status;
    private List<TouristItinerary> passengerItineraries = new ArrayList<>();

    public Mission(Flight flight) {
        this.flight = flight;
    }

    public Mission(String[] data, Flight flight, Vessel vessel) {
        this.key = data[1];
        this.flight = flight;
        this.vessel = vessel;
        this.status = data[4];
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Flight getFlight() {
        return flight;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TouristItinerary> getPassengerItineraries() {
        return passengerItineraries;
    }

    public void addPassengerItinerary(TouristItinerary passengerItinerary) {
        this.passengerItineraries.add(passengerItinerary);
    }

    public void removeFromPassengerItineraries(String name) {
        Iterator iterator = passengerItineraries.listIterator();
        while (iterator.hasNext()) {
            TouristItinerary passengerItinerary = (TouristItinerary) iterator.next();
            if (passengerItinerary.getTourist().getName().equalsIgnoreCase(name)) {
                iterator.remove();
                break;
            }
        }
    }

    void deletePassengerItineraries() {
        this.passengerItineraries = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Mission) {
            Mission that = (Mission) o;
            return this.key.equals(that.key);
        }
        return false;
    }

    @Override
    public int compareTo(Mission that) {
        int comparison = this.status.compareTo(that.getStatus());
        if (comparison == 0) {
            comparison = this.key.compareTo(that.key);
        }
        return comparison;
    }
}
