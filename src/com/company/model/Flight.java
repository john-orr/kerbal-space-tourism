package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private String key;
    private String origin;
    private String destination;
    private String flyby;
    private List<Tourist> customers = new ArrayList<>();

    public Flight(String origin, String destination, String flyby) {
        this.origin = origin.toUpperCase();
        this.destination = destination.toUpperCase();
        if (!flyby.isEmpty()) {
            this.flyby = flyby.toUpperCase();
        }
    }

    public Flight(String[] data) {
        this.key = data[1];
        this.origin = data[2];
        this.destination = data[3];
        if (!data[4].equals("null")) {
            this.flyby = data[4];
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFlyby() {
        return flyby;
    }

    public void setFlyby(String flyby) {
        this.flyby = flyby;
    }

    public List<Tourist> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Tourist> customers) {
        this.customers = customers;
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
}
