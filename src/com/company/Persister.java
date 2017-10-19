package com.company;

import com.company.model.Database;
import com.company.model.Flight;
import com.company.model.Tourist;
import com.company.model.TouristItinerary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Persister {

    public static void write(Database database) throws FileNotFoundException {
        writeTourists(database.getTourists());
        writeFlights(database.getFlights());
        writeTouristItinerary(database.getTourists());
    }

    private static void writeTouristItinerary(List<Tourist> tourists) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/tourist_itinerary.csv"));
        writer.println("H,TOURIST,FLIGHT,PRIORITY,MISSION_KEY");
        for (Tourist tourist : tourists) {
            for (TouristItinerary touristItinerary : tourist.getItinerary()) {
                writer.println("D," + tourist.getName() + "," + touristItinerary.getFlight()
                        .getKey() + "," + touristItinerary.getPriority() + "," + touristItinerary
                        .getMissionKey());
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
