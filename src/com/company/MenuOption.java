package com.company;

public enum MenuOption {
    CANCEL(-1, "Cancel"),
    ADD_NEW_TOURIST(1, "Add New Tourist"),
    VIEW_TOURISTS(2, "View Tourists"),
    ADD_NEW_FLIGHT(3, "Add New Flight"),
    VIEW_FLIGHTS(4, "View Flights"),
    TOURIST_ITINERARY(5, "Tourist Itinerary"),
    CREATE_MISSIONS(6, "Create Missions"),
    VIEW_MISSIONS(7, "View Missions"),
    UPDATE_MISSION_STATUS(8, "Update Mission Status"),
    VIEW_FLIGHT_BOOKINGS(9, "View Flight Bookings"),
    VALIDATE_ITINERARIES(10, "Validate Tourist Itineraries");

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
