package com.company;

import com.company.model.Database;
import com.company.model.Tourist;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static Database database;

    public static void main(String[] args) throws FileNotFoundException {
        init();
        while (true) {
            int choice = 0;
            while (choice == 0) {
                System.out.println("Please choose an operation:");
                System.out.println("\t1. Add New Tourist");
                try {
                    choice = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("That was not a number");
                }
            }
            if (choice == 1) {
                System.out.println("Operation 'Add New Tourist' selected");
                System.out.println("Please enter the name of the new tourist");
                String name = input.nextLine();
                database.addTourist(new Tourist(name));
                Persister.write(database);
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

    static void init() {
        database = new Database();
    }
}
