package com.company.model;

import com.company.Persister;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    List<Tourist> tourists;
    List<Flight> flights;

    public Database(List<Tourist> tourists) {
        this.tourists = tourists;
        this.flights = new ArrayList<>();
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void insertTourist(Tourist tourist) throws FileNotFoundException {
        this.tourists.add(tourist);
        Persister.write(this);
    }

    public String printTourists() {
        String nameColumnFormat = "%-10s";
        StringBuilder output =
                new StringBuilder("TOURISTS\n").append(String.format(nameColumnFormat, "NAME")).append("LOCATION\n");
        for (Tourist tourist : tourists) {
            output.append(String.format(nameColumnFormat, tourist.getName())).append(tourist.getLocation())
                    .append("\n");
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

    public String print() {
        return printTourists();
    }
}
