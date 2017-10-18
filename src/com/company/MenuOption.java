package com.company;

public enum MenuOption {
    ADD_NEW_TOURIST(1, "Add New Tourist"),
    VIEW_TOURISTS(2, "View Tourists"),
    ADD_NEW_FLIGHT(3, "Add New Flight"),
    VIEW_FLIGHTS(4, "View Flights"),
    TOURIST_ITINERARY(5, "Tourist Itinerary");

    int id;
    String text;

    MenuOption(int id, String text) {
        this.id = id;
        this.text = text;
    }

    static MenuOption findById(int id) {
        for (MenuOption option : MenuOption.values()) {
            if (option.id == id) {
                return option;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    String menuDisplay() {
        return "\t" + id + ". " + text;
    }
}