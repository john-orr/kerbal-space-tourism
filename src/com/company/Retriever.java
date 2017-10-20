package com.company;

import com.company.model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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
        Map<String, TouristItinerary> itineraryMap = new HashMap<>();
        Map<String, String> prerequisiteMap = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",");
                if (data[0].equals("H")) {
                    continue;
                }
                String key = data[1];
                Tourist tourist = database.findTourist(data[2]);
                Flight flight = database.findFlight(data[3]);
                String prerequisite = data[4];
                String missionKey = data[5];
                Mission mission = null;
                if (!missionKey.equals("null")) {
                    mission = database.findMission(missionKey);
                }
                TouristItinerary touristItinerary = new TouristItinerary(key, tourist, flight, mission);
                tourist.addToItinerary(touristItinerary);
                flight.addCustomerItinerary(touristItinerary);
                if (mission != null) {
                    mission.addPassengerItinerary(touristItinerary);
                }
                if (prerequisite != null) {
                    itineraryMap.put(key, touristItinerary);
                    prerequisiteMap.put(key, prerequisite);
                }
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
        for (Map.Entry<String, String> prerequisite : prerequisiteMap.entrySet()) {
            TouristItinerary dependentItinerary = itineraryMap.get(prerequisite.getKey());
            TouristItinerary blockingItinerary = itineraryMap.get(prerequisite.getValue());
            dependentItinerary.setPrerequisite(blockingItinerary);
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
