package com.company.model;

public class Flight {

    private String key;
    private String origin;
    private String destination;

    public Flight(String origin, String destination) {
        this.origin = origin.toUpperCase();
        this.destination = destination.toUpperCase();
    }

    public Flight(String[] data) {
        this.key = data[1];
        this.origin = data[2];
        this.destination = data[3];
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

    @Override
    public String toString() {
        return "Flight{" +
                "key='" + key + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
