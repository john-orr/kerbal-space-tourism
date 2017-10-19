package com.company.model;

public class TouristItinerary implements Comparable<TouristItinerary> {

    private Tourist tourist;
    private Flight flight;
    private int priority;
    private String missionKey;

    public TouristItinerary(Tourist tourist, Flight flight, int priority,
            String missionKey) {
        this.tourist = tourist;
        this.flight = flight;
        this.priority = priority;
        if (!missionKey.equals("null")) {
            this.missionKey = missionKey;
        }
    }

    public TouristItinerary(Tourist tourist, Flight flight, int size) {
        this(tourist, flight, size, "null");
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMissionKey() {
        return missionKey;
    }

    public void setMissionKey(String missionKey) {
        this.missionKey = missionKey;
    }

    @Override public int compareTo(TouristItinerary that) {
        return this.priority - that.priority;
    }
}
