package com.company.model;

public class Tourist {

    private String name;
    private String location;

    public Tourist(String name) {
        this.name = name;
        this.location = "";
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
