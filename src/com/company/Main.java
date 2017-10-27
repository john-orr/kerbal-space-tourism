package com.company;

import com.company.model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static com.company.MenuOption.*;

public class Main {

    private static Scanner input = new Scanner(System.in);
    private static Database database;

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
            } else if (choice == VIEW_MISSIONS) {
                viewMissions();
            } else if (choice == UPDATE_MISSION_STATUS) {
                updateMissionStatus();
            } else if (choice == VIEW_FLIGHT_BOOKINGS) {
                viewFlightBookings();
            }
        } while (true);
        Persister.write(database);
    }

    private static void viewFlightBookings() {
        String includePrerequisites = "";
        do {
            database.printFlightBookings(includePrerequisites.equals("i"));
            if (includePrerequisites.equals("i")) {
                System.out.println("Enter e to exclude customers with prerequisites");
            } else {
                System.out.println("Enter i to include customers with prerequisites");
            }
            includePrerequisites = input.nextLine();
        } while (includePrerequisites.equals("i") || includePrerequisites.equals("e"));
    }

    private static void updateMissionStatus() {
        if (!database.printMissions()) {
            return;
        }
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
        if (mission.getStatus().equals("ACTIVE")) {
            String updateStatus;
            do {
                System.out.println("Update mission status to COMPLETED? [y/n]");
                updateStatus = input.nextLine();
            } while (!(updateStatus.equals("y") || updateStatus.equals("n")));
            if (updateStatus.equals("n")) {
                return;
            }
            status = "COMPLETED";
        } else {
            // mission status is ready
            System.out.println("You have two options");
            System.out.println("\t1. Start mission");
            System.out.println("\t2. Cancel mission");
            String option;
            do {
                System.out.println("Please select an option");
                option = input.nextLine();
            } while (!(option.equals("1") || option.equals("2") || option.equals("c")));
            switch (option) {
                case "1":
                    status = "ACTIVE";
                    break;
                case "2":
                    status = "CANCELLED";
                    break;
                default:
                    return;
            }
        }
        switch (status) {
            case "ACTIVE":
                System.out.println("Mission " + mission.getKey() + " started.");
                mission.setStatus(status);
                Collections.sort(database.getMissions());
                break;
            case "CANCELLED":
                database.cancelMission(mission);
                break;
            default:
                database.completeMission(mission);
                for (Tourist tourist : database.getFlaggedTourists()) {
                    String delete;
                    do {
                        System.out.println("Itinerary for " + tourist
                                .getName() + " is now empty. Remove " + tourist
                                .getName() + "? [y/n]");
                        delete = input.nextLine();
                    } while (!(delete.equals("y") || delete.equals("n")));
                    if (delete.equals("y")) {
                        database.removeTourist(tourist);
                    } else {
                        tourist.unFlag();
                    }
                }
                break;
        }
    }

    private static void viewMissions() {
        database.printMissions();
    }

    private static void createMissions() {
        int numFlightsReady = 0;
        flightLoop:
        for (Flight flight : database.getFlights()) {
            Mission mission = new Mission(flight);
            for (TouristItinerary customerItinerary : flight.getCustomerItineraries()) {
                if (customerItinerary.getMission() == null && customerItinerary.getPrerequisite() == null
                        && customerItinerary.getTourist().getLocation().equals(customerItinerary.getFlight().getOrigin())) {
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
                    System.out.println("Enter vessel name");
                    String vessel = input.nextLine();
                    if (vessel.equals("c")) {
                        System.out.println("Mission creation cancelled");
                        continue;
                    }
                    mission.setVessel(vessel.toUpperCase());
                    for (TouristItinerary touristItinerary : mission.getPassengerItineraries()) {
                        touristItinerary.setMission(mission);
                        touristItinerary.getTourist().setLocation(mission.getVessel());
                    }
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
        do {
            Tourist tourist;
            String name;
            do {
                if (!database.printTourists()) {
                    return;
                }
                System.out.println("Please select a tourist");
                name = input.nextLine();
                tourist = database.findTourist(name);
            } while (tourist == null && !name.equals("c"));
            if (tourist == null) {
                return;
            }
            do {
                database.printTouristItinerary(tourist);
                String itineraryAction;
                do {
                    System.out.println("What would you like to do?");
                    System.out.println("\t1. Insert");
                    System.out.println("\t2. Modify");
                    System.out.println("\t3. Delete");
                    itineraryAction = input.nextLine();
                } while (!(itineraryAction.equals("1") || itineraryAction.equals("2") || itineraryAction.equals("3") ||
                        itineraryAction.equals("c")));
                switch (itineraryAction) {
                    case "1":
                        addFlightToItinerary(tourist);
                        break;
                    case "2":
                        modifyOrDeleteItinerary(tourist, "modify");
                        break;
                    case "3":
                        modifyOrDeleteItinerary(tourist, "delete");
                        break;
                }
                if (!isValidItinerary(tourist.getItinerary())) {
                    continue;
                }
                if (itineraryAction.equals("c")) {
                    break;
                }
            } while (true);
        } while (true);
    }

    private static boolean isValidItinerary(List<TouristItinerary> nonBlockingItineraries) {
        // follow the prerequisite chain
        for (TouristItinerary nonBlockingItinerary : nonBlockingItineraries) {
            TouristItinerary itinerary = nonBlockingItinerary;
            while (itinerary != null) {
                if (itinerary.getPrerequisite() == null) {
                    if (!itinerary.getFlight().getOrigin().equals("KERBIN")) {
                        System.out.println("*****Itinerary without prerequisite does not originate at KERBIN. key=" +
                                itinerary.getKey());
                        return false;
                    }
                } else if (!itinerary.getFlight().getOrigin().equals(itinerary.getPrerequisite().getFlight().getDestination())) {
                    System.out.println(
                            "*****Itinerary origin does not match prerequisite destination. key=" + itinerary.getKey());
                    return false;
                }
                itinerary = itinerary.getPrerequisite();
            }
        }
        return true;
    }

    private static void modifyOrDeleteItinerary(Tourist tourist, String mode) {
        do {
            database.printTouristItinerary(tourist);
            TouristItinerary itinerary;
            String itineraryKey;
            do {
                System.out.println("Please enter the key of the itinerary to " + mode);
                itineraryKey = input.nextLine();
                itinerary = tourist.findItinerary(itineraryKey);
                if (itinerary != null && itinerary.getMission() != null) {
                    System.out.println("Action '" + mode + "' is not possible as a mission for this itinerary exists");
                    itinerary = null;
                }
            } while (itinerary == null && !itineraryKey.equals("c"));
            if (itinerary == null) {
                return;
            }
            switch (mode) {
                case "modify":
                    modifyItinerary(tourist, itinerary);
                    break;
                case "delete":
                    removeItinerary(tourist, itinerary);
                    break;
            }
        } while (true);
    }

    private static void removeItinerary(Tourist tourist, TouristItinerary itinerary) {
        String patchPrerequisites = "n";
        if (itinerary.getPrerequisite() != null) {
            do {
                System.out.println("Patch up the prerequisites? [y/n]");
                patchPrerequisites = input.nextLine();
            } while (!(patchPrerequisites.equals("y") || patchPrerequisites.equals("n")));
        }
        tourist.removeFromItinerary(itinerary, patchPrerequisites.equals("y"));
    }

    private static void modifyItinerary(Tourist tourist, TouristItinerary itinerary) {
        String itineraryKey;
        TouristItinerary prerequisite;
        do {
            System.out.println("Enter key of the prerequisite itinerary (n if none)");
            itineraryKey = input.nextLine();
            prerequisite = tourist.findItinerary(itineraryKey);
            if (prerequisite != null &&
                    !itinerary.getFlight().getOrigin().equals(prerequisite.getFlight().getDestination())) {
                System.out.println("Seems like an invalid prerequisite");
                prerequisite = null;
            }
        } while (prerequisite == null && !itineraryKey.equals("n"));
        itinerary.setPrerequisite(prerequisite);
    }

    private static void addFlightToItinerary(Tourist tourist) {
        boolean first = true;
        do {
            List<String> availableFlightKeys;
            String flightKey = null;
            do {
                if (flightKey != null) {
                    System.out.println("Invalid flight key");
                }
                if (first) {
                    first = false;
                    database.printTouristItinerary(tourist);
                }
                availableFlightKeys = database.printAvailableFlights(tourist);
                if (availableFlightKeys.isEmpty()) {
                    System.out.println("No available flights");
                    break;

                }
                System.out.println("Please enter the key of the flight to add to the itinerary");
                flightKey = input.nextLine();
            } while (!(availableFlightKeys.contains(flightKey) || flightKey.equals("c")));
            Flight flight = database.findFlight(flightKey);
            if (flight == null) {
                break;
            }
            TouristItinerary prerequisite = null;
            if (!tourist.getItinerary().isEmpty()) {
                database.printTouristItinerary(tourist);
                String itineraryKey;
                do {
                    System.out.println("Please enter the key of the prerequisite itinerary (n if none)");
                    itineraryKey = input.nextLine();
                    prerequisite = tourist.findItinerary(itineraryKey);
                    if (prerequisite != null && !flight.getOrigin().equals(prerequisite.getFlight().getDestination())) {
                        System.out.println("Seems like an invalid prerequisite");
                        prerequisite = null;
                    }
                } while (prerequisite == null && !itineraryKey.equals("n"));
            }
            database.insertItinerary(tourist, flight, prerequisite);
            database.printTouristItinerary(tourist);
        } while (true);
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

    private static void init() throws IOException {
        database = Retriever.read();
    }
}
