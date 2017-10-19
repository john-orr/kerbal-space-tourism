package com.company.model;

import java.util.List;

public class Mission {

    private Flight flight;
    private List<Tourist> passengers;
    private String vessel;

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

    public String getVessel() {
        return vessel;
    }

    public void setVessel(String vessel) {
        this.vessel = vessel;
    }
}
