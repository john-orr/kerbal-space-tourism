package com.company;

import com.company.model.Database;
import com.company.model.Flight;
import com.company.model.Tourist;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.company.MenuOption.*;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Database database;

    public static void main(String[] args) throws IOException {
        init();
        String repeat;
        do {
            MenuOption choice = menu();
            System.out.println("Operation '" + choice.getText() + "' selected");
            if (choice == ADD_NEW_TOURIST) {
                addNewTourist();
            } else if (choice == VIEW_TOURISTS) {
                viewTourists();
            } else if (choice == ADD_NEW_FLIGHT) {
                addNewFlight();
            } else if (choice == VIEW_FLIGHTS) {
                viewFlights();
            } else if (choice == TOURIST_ITINERARY) {
                touristItinerary();
            }

            do {
                System.out.println("Perform another operation? [y/n]");
                repeat = input.nextLine();
            } while (!(repeat.equals("y") || repeat.equals("n")));
        } while (repeat.equals("y"));
    }

    private static void touristItinerary() throws FileNotFoundException {
        String touristRepeat;
        do {
            Tourist tourist;
            do {
                database.printTourists();
                System.out.println("Please select a tourist");
                String name = input.nextLine();
                tourist = database.findTourist(name);
            } while (tourist == null);
            String flightRepeat;
            do {
                Flight flight = null;
                do {
                    if (!tourist.getItinerary().isEmpty()) {
                        database.printTouristItinerary(tourist);
                    }
                    if (tourist.getItinerary().size() != database.getFlights().size()) {
                        database.printAvailableFlights(tourist);
                    } else {
                        System.out.println("No available flights");
                        break;
                    }
                    System.out.println("Please enter the key of the flight to add to the itinerary");
                    String key = input.nextLine();
                    flight = database.findFlight(key);
                } while (flight == null);
                if (flight == null) {
                    break;
                }
                database.insertTouristItinerary(tourist, flight);
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
        System.out.println("Please enter the destination of the new flight");
        String destination = input.nextLine();
        System.out.println("Enter any flyby events on this flight");
        String flyby = input.nextLine();
        database.insertFlight(new Flight(origin, destination, flyby));
    }

    private static void viewTourists() {
        database.printTourists();
    }

    private static void addNewTourist() throws FileNotFoundException {
        System.out.println("Please enter the name of the new tourist");
        String name = input.nextLine();
        database.insertTourist(new Tourist(name));
    }

    private static MenuOption menu() {
        do {
            System.out.println("Please choose an operation:");
            for (MenuOption option : MenuOption.values()) {
                System.out.println(option.menuDisplay());
            }
            int choice = 0;
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("That was not a number");
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
