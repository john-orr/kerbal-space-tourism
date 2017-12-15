package com.company;

import com.company.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

class Retriever {

    static Database read(Statement statement) throws SQLException {
        List<Tourist> tourists = readTourists(statement);
        List<Flight> flights = readFlights(statement);
        Collections.sort(flights);
        List<Vessel> vessels = readVessels(statement);
        Database database = new Database(tourists, flights, vessels);
        buildMissions(statement, database);
        buildItinerary(statement, database);
        return database;
    }

    private static List<Vessel> readVessels(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM VESSEL");
        List<Vessel> vessels = new ArrayList<>();
        while (resultSet.next()) {
            vessels.add(new Vessel(
                    resultSet.getString("NAME"),
                    resultSet.getString("LOCATION"),
                    resultSet.getInt("CAPACITY")));
        }
        return vessels;
    }

    private static void buildMissions(Statement statement, Database database) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MISSION");
        while (resultSet.next()) {
            Flight flight = database.findFlight(resultSet.getInt("FLIGHTID"));
            Vessel vessel = database.findVessel(resultSet.getString("VESSEL"));
            Mission mission = new Mission(
                    resultSet.getInt("ID"),
                    flight,
                    vessel,
                    resultSet.getString("STATUS"));
            database.addMission(mission);
        }
    }

    private static void buildItinerary(Statement statement, Database database) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TOURIST_ITINERARY");
        Map<Integer, TouristItinerary> itineraryMap = new HashMap<>();
        Map<Integer, Integer> prerequisiteMap = new HashMap<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("ID");
            Tourist tourist = database.findTourist(resultSet.getString("TOURIST"));
            Flight flight = database.findFlight(resultSet.getInt("FLIGHTID"));
            int prerequisite = resultSet.getInt("PREREQUISITE");
            int missionId = resultSet.getInt("MISSIONID");
            Mission mission = database.findMission(missionId);
            TouristItinerary touristItinerary = new TouristItinerary(id, tourist, flight, mission);
            tourist.addToItinerary(touristItinerary);
            flight.addCustomerItinerary(touristItinerary);
            if (mission != null) {
                mission.addPassengerItinerary(touristItinerary);
            }
            itineraryMap.put(id, touristItinerary); // in case something has touristItinerary as their prerequisite
            if (prerequisite != 0) {
                prerequisiteMap.put(id, prerequisite);
            }
        }
        for (Map.Entry<Integer, Integer> prerequisite : prerequisiteMap.entrySet()) {
            TouristItinerary dependentItinerary = itineraryMap.get(prerequisite.getKey());
            TouristItinerary blockingItinerary = itineraryMap.get(prerequisite.getValue());
            dependentItinerary.setPrerequisite(blockingItinerary);
        }
    }

    private static List<Tourist> readTourists(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM TOURIST");
        List<Tourist> tourists = new ArrayList<>();
        while (resultSet.next()) {
            tourists.add(new Tourist(
                    resultSet.getString("NAME"),
                    resultSet.getString("LOCATION")));
        }
        return tourists;
    }

    private static List<Flight> readFlights(Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM FLIGHT");
        List<Flight> flights = new ArrayList<>();
        while (resultSet.next()) {
            flights.add(new Flight(
                    resultSet.getInt("ID"),
                    resultSet.getString("ORIGIN"),
                    resultSet.getString("DESTINATION"),
                    resultSet.getString("FLYBY"),
                    resultSet.getInt("CAPACITY")));
        }
        return flights;
    }
}
