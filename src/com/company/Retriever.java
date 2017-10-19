package com.company;

import com.company.model.*;

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
        buildMissions(database);
        buildItinerary(database);
        return database;
    }

    private static void buildMissions(Database database) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/missions.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",");
                if (data[0].equals("H")) {
                    continue;
                }
                Flight flight = database.findFlight(data[2]);
                Mission mission = new Mission(data, flight);
                database.getMissions().add(mission);
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
    }

    private static void buildItinerary(Database database) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/tourist_itinerary.csv")));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",");
                if (data[0].equals("H")) {
                    continue;
                }
                Tourist tourist = database.findTourist(data[1]);
                Flight flight = database.findFlight(data[2]);
                int priority = Integer.parseInt(data[3]);
                String missionKey = data[4];
                Mission mission = null;
                if (!missionKey.equals("null")) {
                    mission = database.findMission(missionKey);
                }
                TouristItinerary touristItinerary =
                        new TouristItinerary(tourist, flight, priority, mission);
                tourist.addToItinerary(touristItinerary);
                flight.addCustomerItinerary(touristItinerary);
                if (mission != null) {
                    mission.addPassengerItinerary(touristItinerary);
                    mission.addPassenger(tourist);
                }
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
                String[] data = line.split(",");
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
                String[] data = line.split(",");
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
