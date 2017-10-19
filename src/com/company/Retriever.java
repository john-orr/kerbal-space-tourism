package com.company;

import com.company.model.Database;
import com.company.model.Flight;
import com.company.model.Tourist;
import com.company.model.TouristItinerary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Retriever {

    static Database read() throws IOException {
        List<Tourist> tourists = readTourists();
        List<Flight> flights = readFlights();
        Collections.sort(flights);
        Database database = new Database(tourists, flights);
        buildItinerary(database);
        return database;
    }

    private static void buildItinerary(Database database) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/tourist_itinerary.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",", 5);
                if (data[0].equals("H")) {
                    continue;
                }
                Tourist tourist = database.findTourist(data[1]);
                Flight flight = database.findFlight(data[2]);
                int priority = Integer.parseInt(data[3]);
                String missionKey = data[4];
                TouristItinerary touristItinerary = new TouristItinerary(tourist, flight, priority, missionKey);
                tourist.addToItinerary(touristItinerary);
                flight.addCustomerItinerary(touristItinerary);
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
    }

    private static List<Tourist> readTourists() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/tourists.csv")));
        String line;
        List<Tourist> tourists = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",", 3);
                if (data[0].equals("H")) {
                    continue;
                }
                tourists.add(new Tourist(data));
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
        return tourists;
    }

    private static List<Flight> readFlights() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/flights.csv")));
        String line;
        List<Flight> flights = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",", 5);
                if (data[0].equals("H")){
                    continue;
                }
                flights.add(new Flight(data));
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
        return flights;
    }
}
