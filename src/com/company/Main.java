package com.company;

import com.company.model.Database;
import com.company.model.Tourist;

import java.io.IOException;
import java.util.Scanner;

import static com.company.MenuOption.*;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Database database;

    public static void main(String[] args) throws IOException {
        init();
        while (true) {
            MenuOption choice = menu();
            System.out.println("Operation '" + choice.getText() + "' selected");
            if (choice == ADD_NEW_TOURIST) {
                System.out.println("Please enter the name of the new tourist");
                String name = input.nextLine();
                database.addTourist(new Tourist(name));
                Persister.write(database);
            } else if (choice == VIEW_TOURISTS) {
                System.out.println(database.printTourists());
            }

            String repeat = "";
            while (!repeat.equals("y") && !repeat.equals("n")) {
                System.out.println("Perform another operation?[y/n]");
                repeat = input.nextLine();
            }
            if (repeat.equals("n")) {
                break;
            }
        }
    }

    private static MenuOption menu() {
        while (true) {
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
        }
    }

    static void init() throws IOException {
        database = Retriever.read();
    }
}
