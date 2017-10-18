package com.company.model;

public class Tourist {

    private String name;
    private String location;

    public Tourist(String name) {
        this.name = name.toUpperCase();
        this.location = "KERBIN";
    }

    public Tourist(String[] data) {
        this.name = data[1];
        this.location = data[2];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tourist{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
