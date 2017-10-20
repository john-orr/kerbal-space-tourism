package com.company;

import com.company.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.company.MenuOption.*;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Database database;

    public static void main(String[] args) throws IOException {
        init();
        do {
            MenuOption choice = menu();
            System.out.println("Operation '" + choice.getText() + "' selected");
            if (choice == CANCEL) {
                break;
            } else if (choice == ADD_NEW_TOURIST) {
                addNewTourist();
            } else if (choice == VIEW_TOURISTS) {
                viewTourists();
            } else if (choice == ADD_NEW_FLIGHT) {
                addNewFlight();
            } else if (choice == VIEW_FLIGHTS) {
                viewFlights();
            } else if (choice == TOURIST_ITINERARY) {
                touristItinerary();
            } else if (choice == CREATE_MISSIONS) {
                createMissions();
            } else if (choice == UPDATE_MISSION_STATUS) {
                updateMissionStatus();
            }
        } while (true);
        Persister.write(database);
    }

    private static void updateMissionStatus() {
        database.printMissions();
        Mission mission;
        do {
            System.out.println("Please select a mission");
            String missionKey = input.nextLine();
            if (missionKey.equals("c")) {
                System.out.println("Update mission cancelled");
                return;
            }
            mission = database.findMission(missionKey);
        } while (mission == null);
        String status;
        do {
            System.out.println("Please enter the new status");
            status = input.nextLine().toUpperCase();
        } while (!(status.equals("ACTIVE") || status.equals("COMPLETED")));
        if (status.equals("ACTIVE")) {
            mission.setStatus(status);
        } else {
            database.completeMission(mission);
            for (Tourist tourist : database.getFlaggedTourists()) {
                String delete;
                do {
                    System.out.println("Itinerary for " + tourist.getName() + " is now empty. Remove " + tourist.getName() + "? [y/n]");
                    delete = input.nextLine();
                } while (!(delete.equals("y") || delete.equals("n")));
                if (delete.equals("y")) {
                    database.removeTourist(tourist);
                } else {
                    tourist.unflag();
                }
            }
        }
    }

    private static void createMissions() {
        int numFlightsReady = 0;
        flightLoop:
        for (Flight flight : database.getFlights()) {
            Mission mission = new Mission(flight);
            for (TouristItinerary customerItinerary : flight.getCustomerItineraries()) {
                if (customerItinerary.getMission() == null && customerItinerary.equals(
                        customerItinerary.getTourist().getNextItineraryItem())) {
                    mission.addPassengerItinerary(customerItinerary);
                }
            }
            if (mission.getPassengerItineraries().size() >= flight.getCapacity()) {
                numFlightsReady++;
                StringBuilder output =
                        new StringBuilder("Flight ").append(flight.getKey()).append(" from ")
                                .append(flight.getOrigin()).append(" to ")
                                .append(flight.getDestination());
                if (flight.getFlyby() != null) {
                    output.append(" (flyby ").append(flight.getFlyby()).append(")");
                }
                output.append(" is ready to go with passengers: ");
                for (TouristItinerary passengerItinerary : mission.getPassengerItineraries()) {
                    output.append(passengerItinerary.getTourist().getName()).append("\t");
                }
                System.out.println(output.toString());
                String createMission;
                do {
                    System.out.println("Would you like to create the mission? [y/n]");
                    createMission = input.nextLine();
                } while (!(createMission.equals("y") || createMission.equals("n")));
                if (createMission.equals("y")) {
                    boolean showItineraries = false;
                    while (mission.getPassengerItineraries().size() > flight.getCapacity()) {
                        if (showItineraries) {
                            for (TouristItinerary passengerItinerary : mission.getPassengerItineraries()) {
                                database.printTouristItinerary(passengerItinerary.getTourist());
                            }
                        }
                        System.out.println("Who should be left behind?");
                        for (TouristItinerary passengerItinerary : mission.getPassengerItineraries()) {
                            System.out.println(passengerItinerary.getTourist().getName());
                        }
                        if (!showItineraries) {
                            System.out.println("Enter i if you want to see their itineraries");
                        }
                        String name = input.nextLine();
                        showItineraries = false;
                        switch (name) {
                            case "i":
                                showItineraries = true;
                                break;
                            case "c":
                                System.out.println("Mission creation cancelled");
                                continue flightLoop;
                            default:
                                mission.removeFromPassengerItineraries(name);
                                break;
                        }
                    }
                    mission.setStatus("READY");
                    for (TouristItinerary touristItinerary : mission.getPassengerItineraries()) {
                        touristItinerary.setMission(mission);
                    }
                    System.out.println("Enter vessel name");
                    String vessel = input.nextLine();
                    if (vessel.equals("c")) {
                        System.out.println("Mission creation cancelled");
                        continue;
                    }
                    mission.setVessel(vessel.toUpperCase());
                    database.insertMission(mission);
                }
            }
        }
        if (numFlightsReady == 0) {
            System.out.println("No flights are ready to go");
        } else {
            System.out.println("No more flights are ready to go");
        }
    }

    private static void touristItinerary() throws FileNotFoundException {
        String touristRepeat;
        do {
            Tourist tourist;
            String name;
            do {
                boolean valid = database.printTourists();
                if (!valid) {
                    return;
                }
                System.out.println("Please select a tourist");
                name = input.nextLine();
                tourist = database.findTourist(name);
            } while (tourist == null && !name.equals("c"));
            if (tourist == null) {
                return;
            }
            String flightRepeat = null;
            do {
                Flight flight = null;
                String key;
                do {
                    if (!tourist.getItinerary().isEmpty() && flightRepeat == null) {
                        database.printTouristItinerary(tourist);
                    }
                    boolean valid = database.printAvailableFlights(tourist);
                    if (!valid) {
                        System.out.println("No available flights");
                        break;

                    }
                    System.out.println("Please enter the key of the flight to add to the itinerary");
                    key = input.nextLine();
                    flight = database.findFlight(key);
                } while (flight == null && !key.equals("c"));
                if (flight == null) {
                    break;
                }
                tourist.addToItinerary(flight);
                database.printTouristItinerary(tourist);
                do {
                    System.out.println("Add another flight? [y/n]");
                    flightRepeat = input.nextLine();
                } while (!(flightRepeat.equals("y") || flightRepeat.equals("n")));
            } while (flightRepeat.equals("y"));
            do {
                System.out.println("Switch tourist? [y/n]");
                touristRepeat = input.nextLine();
            } while (!(touristRepeat.equals("y") || touristRepeat.equals("n")));
        } while (touristRepeat.equals("y"));
    }

    private static void viewFlights() {
        database.printFlights();
    }

    private static void addNewFlight() throws FileNotFoundException {
        System.out.println("Please enter the origin of the new flight");
        String origin = input.nextLine();
        if (origin.equals("c")) {
            return;
        }
        System.out.println("Please enter the destination of the new flight");
        String destination = input.nextLine();
        if (destination.equals("c")) {
            return;
        }
        System.out.println("Enter any flyby events on this flight");
        String flyby = input.nextLine();
        if (flyby.equals("c")) {
            return;
        }
        String capacity;
        do {
            System.out.println("Please enter the capacity of the new flight");
            capacity = input.nextLine();
            if (capacity.equals("c")) {
                return;
            }
        } while (!capacity.matches("^\\d+$"));
        database.insertFlight(new Flight(origin, destination, flyby, Integer.parseInt(capacity)));
    }

    private static void viewTourists() {
        database.printTourists();
    }

    private static void addNewTourist() throws FileNotFoundException {
        System.out.println("Please enter the name of the new tourist");
        String name = input.nextLine();
        if (name.equals("c")) {
            return;
        }
        database.insertTourist(new Tourist(name));
    }

    private static MenuOption menu() {
        do {
            System.out.println("Please choose an operation:");
            for (MenuOption option : MenuOption.values()) {
                if (option != CANCEL) {
                    System.out.println(option.menuDisplay());
                }
            }
            int choice = 0;
            String userEntry = input.nextLine();
            try {
                choice = Integer.parseInt(userEntry);
            } catch (NumberFormatException e) {
                if (userEntry.equals("c")) {
                    choice = -1;
                } else {
                    System.out.println("That was not a number");
                }
            }
            MenuOption menuOption = findById(choice);
            if (menuOption == null) {
                System.out.println("Invalid selection");
            } else {
                return menuOption;
            }
        } while (true);
    }

    static void init() throws IOException {
        database = Retriever.read();
    }
}
