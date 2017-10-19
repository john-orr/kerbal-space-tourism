package com.company;

import com.company.model.Database;
import com.company.model.Flight;
import com.company.model.Tourist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Persister {

    public static void write(Database database) throws FileNotFoundException {
        System.out.println("Writing to database");
        writeTourists(database.getTourists());
        writeFlights(database.getFlights());
        writeTouristItinerary(database.getTourists());
    }

    private static void writeTouristItinerary(List<Tourist> tourists) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/tourist_itinerary.csv"));
        writer.println("H,TOURIST,FLIGHT,PRIORITY");
        for (Tourist tourist : tourists) {
            for (Map.Entry<Integer, Flight> flight : tourist.getItinerary().entrySet()) {
                Integer priority = flight.getKey();
                String flightKey = flight.getValue().getKey();
                writer.println("D," + tourist.getName() + "," + flightKey + "," + priority);
            }
        }
        writer.close();
    }

    private static void writeTourists(List<Tourist> tourists) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/tourists.csv"));
        writer.println("H,NAME,LOCATION");
        for (Tourist tourist : tourists) {
            writer.println("D," + tourist.getName() + "," + tourist.getLocation());
        }
        writer.close();
    }

    private static void writeFlights(List<Flight> flights) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/flights.csv"));
        writer.println("H,KEY,ORIGIN,DESTINATION,FLYBY");
        for (Flight flight : flights) {
            writer.println("D," + flight.getKey() + "," + flight.getOrigin() + "," + flight
                    .getDestination() + "," + flight.getFlyby());
        }
        writer.close();
    }
}
