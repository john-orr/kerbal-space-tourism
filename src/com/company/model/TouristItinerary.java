package com.company.model;

public class TouristItinerary extends EntityWithNumericId {

    private int id;
    private Tourist tourist;
    private Flight flight;
    private TouristItinerary prerequisite;
    private Mission mission;

    private TouristItinerary(int id, Tourist tourist, Flight flight, Mission mission, TouristItinerary prerequisite) {
        this.id = id;
        this.tourist = tourist;
        this.flight = flight;
        this.mission = mission;
        this.prerequisite = prerequisite;
    }

    public TouristItinerary(int id, Tourist tourist, Flight flight, Mission mission) {
        this(id, tourist, flight, mission, null);
    }

    TouristItinerary(int id, Tourist tourist, Flight flight, TouristItinerary prerequisite) {
        this(id, tourist, flight, null, prerequisite);
    }

    public int getId() {
        return id;
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
            return this.id ==  that.id;
        }
        return false;
    }
}
