package com.company.model;

import com.company.Persister;

import java.io.FileNotFoundException;
import java.util.List;

public class Database {

    List<Tourist> tourists;
    List<Flight> flights;
    public static final String COLUMN_FORMAT = "%-10s";

    public Database(List<Tourist> tourists, List<Flight> flights) {
        this.tourists = tourists;
        this.flights = flights;
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void insertTourist(Tourist tourist) throws FileNotFoundException {
        this.tourists.add(tourist);
        Persister.write(this);
    }

    public String printTourists() {
        StringBuilder output = new StringBuilder("TOURISTS\n")
                .append(tableCell("NAME"))
                .append(tableCell("LOCATION")).append("\n");
        for (Tourist tourist : tourists) {
            output.append(tableCell(tourist.getName()))
                    .append(tableCell(tourist.getLocation())).append("\n");
        }
        return output.toString();
    }

    public List<Flight> getFlights() {
        return flights;
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

    public String printFlights() {
        StringBuilder output = new StringBuilder("FLIGHTS\n")
                .append(tableCell("KEY"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION")).append("\n");
        for (Flight flight : flights) {
            output.append(tableCell(flight.getKey()))
                    .append(tableCell(flight.getOrigin()))
                    .append(tableCell(flight.getDestination())).append("\n");
        }
        return output.toString();
    }

    private String tableCell(String content) {
        return String.format(COLUMN_FORMAT, content);
    }

    public String print() {
        return printTourists() + printFlights();
    }
}
