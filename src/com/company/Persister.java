package com.company;

import com.company.model.Database;
import com.company.model.Tourist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Persister {

    static void write(Database database) throws FileNotFoundException {
        System.out.println("Writing to database");
        PrintWriter writer = new PrintWriter(new File("database/tourists.csv"));
        writer.println("H,NAME,LOCATION");
        for (Tourist tourist : database.getTourists()) {
            writer.println("D," + tourist.getName() + "," + tourist.getLocation());
        }
        writer.close();
    }
}
