package com.company.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mission extends EntityWithNumericId implements Comparable<Mission> {

    private int id;
    private Flight flight;
    private Vessel vessel;
    private String status;
    private List<TouristItinerary> passengerItineraries = new ArrayList<>();

    public Mission(Flight flight) {
        this.flight = flight;
    }

    public Mission(int id, Flight flight, Vessel vessel, String status) {
        this.id = id;
        this.flight = flight;
        this.vessel = vessel;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
            return this.id == that.id;
        }
        return false;
    }

    @Override
    public int compareTo(Mission that) {
        int comparison = this.status.compareTo(that.getStatus());
        if (comparison == 0) {
            comparison = this.id - that.id;
        }
        return comparison;
    }
}
