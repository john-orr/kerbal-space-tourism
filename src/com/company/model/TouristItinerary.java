package com.company.model;

public class TouristItinerary implements Comparable<TouristItinerary> {

    private Tourist tourist;
    private Flight flight;
    private int priority;
    private Mission mission;

    public TouristItinerary(Tourist tourist, Flight flight, int priority, Mission mission) {
        this.tourist = tourist;
        this.flight = flight;
        this.priority = priority;
        this.mission = mission;
    }

    public TouristItinerary(Tourist tourist, Flight flight, int size) {
        this(tourist, flight, size, null);
    }

    public Tourist getTourist() {
        return tourist;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getPriority() {
        return priority;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TouristItinerary) {
            TouristItinerary that = (TouristItinerary) o;
            return this.tourist.equals(that.tourist) && this.flight.equals(that.flight) && this.priority == that.priority;
        }
        return false;
    }

    @Override public int compareTo(TouristItinerary that) {
        return this.priority - that.priority;
    }
}
