package com.company.model;

import com.company.Persister;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

public class Database {

    List<Tourist> tourists;
    List<Flight> flights;
    public static final String COLUMN_FORMAT = "%-15s";

    public Database(List<Tourist> tourists, List<Flight> flights) {
        this.tourists = tourists;
        this.flights = flights;
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public Tourist findTourist(String name) {
        for (Tourist tourist : tourists) {
            if (tourist.getName().equalsIgnoreCase(name)) {
                return tourist;
            }
        }
        return null;
    }

    public void insertTourist(Tourist tourist) throws FileNotFoundException {
        this.tourists.add(tourist);
        Persister.write(this);
    }

    public void printTourists() {
        StringBuilder output = new StringBuilder("TOURISTS\n")
                .append(tableCell("NAME"))
                .append(tableCell("LOCATION")).append("\n");
        for (Tourist tourist : tourists) {
            output.append(tableCell(tourist.getName()))
                    .append(tableCell(tourist.getLocation())).append("\n");
        }
        System.out.println(output.toString());
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public Flight findFlight(String key) {
        for (Flight flight : flights) {
            if (flight.getKey().equals(key)) {
                return flight;
            }
        }
        return null;
    }

    public void insertFlight(Flight newFlight) throws FileNotFoundException {
        int keyGen = 0;
        for (Flight flight : flights) {
            int flightKey = Integer.parseInt(flight.getKey());
            if (Integer.parseInt(flight.getKey()) > keyGen) {
                keyGen = flightKey;
            }
        }
        newFlight.setKey(String.format("%03d", keyGen + 1));
        this.flights.add(newFlight);
        Persister.write(this);
    }

    public void printFlights() {
        printFlights(Collections.<String>emptyList());
    }

    public void printFlights(List<String> excludeKeys) {
        StringBuilder output = new StringBuilder("FLIGHTS\n")
                .append(tableCell("KEY"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION")).append("\n");
        for (Flight flight : flights) {
            if (!excludeKeys.contains(flight.getKey())) {
                output.append(tableCell(flight.getKey()))
                        .append(tableCell(flight.getOrigin()))
                        .append(tableCell(flight.getDestination())).append("\n");
            }
        }
        System.out.println(output.toString());
    }

    private String tableCell(String content) {
        return String.format(COLUMN_FORMAT, content);
    }

    public void print() {
        printTourists();
        printFlights();
    }

    public void printTouristItinerary(Tourist tourist) {
        StringBuilder output = new StringBuilder("ITINERARY\n")
                .append(tableCell("NAME"))
                .append(tableCell("F.KEY"))
                .append(tableCell("F.ORIGIN"))
                .append(tableCell("F.DESTINATION")).append("\n");
        for (Flight flight : tourist.getItinerary()) {
            output.append(tableCell(tourist.getName()))
                    .append(tableCell(flight.getKey()))
                    .append(tableCell(flight.getOrigin()))
                    .append(tableCell(flight.getDestination())).append("\n");
        }
        System.out.println(output.toString());
    }

    public void printAvailableFlights(Tourist tourist) {
        List<String> flightKeys = tourist.getFlightKeys();
        printFlights(flightKeys);
    }

    public void insertTouristItinerary(Tourist tourist, Flight flight) throws FileNotFoundException {
        tourist.addToItinerary(flight);
        Persister.write(this);
    }
}
