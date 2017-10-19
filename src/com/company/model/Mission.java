package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Mission {

    private String key;
    private Flight flight;
    private List<Tourist> passengers = new ArrayList<>();
    private String vessel;
    private String status;
    private List<TouristItinerary> passengerItineraries = new ArrayList<>();

    public Mission(Flight flight) {
        this.flight = flight;
    }

    public Mission(String[] data, Flight flight) {
        this.key = data[1];
        this.flight = flight;
        this.vessel = data[3];
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

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public List<Tourist> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Tourist> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger(Tourist passenger) {
        this.passengers.add(passenger);
    }

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
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

    public void setPassengerItineraries(
            List<TouristItinerary> passengerItineraries) {
        this.passengerItineraries = passengerItineraries;
    }

    public void addPassengerItinerary(TouristItinerary passengerItinerary) {
        this.passengerItineraries.add(passengerItinerary);
    }
}
