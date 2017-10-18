package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Flight {

    private String key;
    private String origin;
    private String destination;
    private List<Tourist> customers;

    public Flight(String origin, String destination) {
        this.origin = origin.toUpperCase();
        this.destination = destination.toUpperCase();
        this.customers = new ArrayList<>();
    }

    public Flight(String[] data) {
        this.key = data[1];
        this.origin = data[2];
        this.destination = data[3];
        this.customers = new ArrayList<>();
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

    public List<Tourist> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Tourist> customers) {
        this.customers = customers;
    }
}
