package com.company.model;

public class TouristItinerary extends Entity {

    private String key;
    private Tourist tourist;
    private Flight flight;
    private TouristItinerary prerequisite;
    private Mission mission;

    private TouristItinerary(String key, Tourist tourist, Flight flight, Mission mission, TouristItinerary prerequisite) {
        this.key = key;
        this.tourist = tourist;
        this.flight = flight;
        this.mission = mission;
        this.prerequisite = prerequisite;
    }

    public TouristItinerary(String key, Tourist tourist, Flight flight, Mission mission) {
        this(key, tourist, flight, mission, null);
    }

    TouristItinerary(String key, Tourist tourist, Flight flight, TouristItinerary prerequisite) {
        this(key, tourist, flight, null, prerequisite);
    }

    public String getKey() {
        return key;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public Flight getFlight() {
        return flight;
    }

    public TouristItinerary getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(TouristItinerary prerequisite) {
        this.prerequisite = prerequisite;
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
            return this.key.equals(that.key);
        }
        return false;
    }
}
