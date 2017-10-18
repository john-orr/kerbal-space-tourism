package com.company;

import com.company.model.Database;
import com.company.model.Flight;
import com.company.model.Tourist;

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
            if (choice == ADD_NEW_TOURIST) {
                System.out.println("Please enter the name of the new tourist");
                String name = input.nextLine();
                database.insertTourist(new Tourist(name));
            } else if (choice == VIEW_TOURISTS) {
                System.out.println(database.printTourists());
            } else if (choice == ADD_NEW_FLIGHT) {
                System.out.println("Please enter the origin of the new flight");
                String origin = input.nextLine();
                System.out.println("Please enter the destination of the new flight");
                String destination = input.nextLine();
                database.insertFlight(new Flight(origin, destination));
            }else if (choice == VIEW_FLIGHTS) {
                System.out.println(database.printFlights());
            }

            String repeat;
            do {
                System.out.println("Perform another operation?[y/n]");
                repeat = input.nextLine();
            } while (!repeat.equals("y") && !repeat.equals("n"));
            if (repeat.equals("n")) {
                break;
            }
        } while (true);
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
