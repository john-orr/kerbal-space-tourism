package com.company;

import com.company.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

class Persister {

    static void write(Database database) throws FileNotFoundException {
        writeTourists(database.getTourists());
        writeFlights(database.getFlights());
        writeTouristItinerary(database.getTourists());
        writeMissions(database.getMissions());
        writeVessels(database.getVessels());
    }

    private static void writeVessels(List<Vessel> vessels) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/vessels.csv"));
        writer.println("H,NAME,LOCATION,CAPACITY");
        for (Vessel vessel : vessels) {
            writer.println("D," + vessel.getName() + "," + vessel.getLocation() + "," + vessel.getCapacity());
        }
        writer.flush();
        writer.close();
    }

    private static void writeMissions(List<Mission> missions) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/missions.csv"));
        writer.println("H,ID,FLIGHT_ID,VESSEL,STATUS");
        for (Mission mission : missions) {
            writer.println(
                    "D," + mission.getId() + "," + mission.getFlight().getId() + "," + mission
                            .getVessel().getName() + "," + mission.getStatus());
        }
        writer.flush();
        writer.close();
    }

    private static void writeTouristItinerary(List<Tourist> tourists) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/tourist_itinerary.csv"));
        writer.println("H,ID,TOURIST,FLIGHT,PREREQUISITE,MISSION_ID");
        for (Tourist tourist : tourists) {
            for (TouristItinerary touristItinerary : tourist.getItinerary()) {
                StringBuilder itineraryRecord = new StringBuilder()
                        .append("D,").append(touristItinerary.getId())
                        .append(",").append(tourist.getName())
                        .append(",").append(touristItinerary.getFlight().getId())
                        .append(",");
                if (touristItinerary.getPrerequisite() != null) {
                    itineraryRecord.append(touristItinerary.getPrerequisite().getId());
                } else {
                    itineraryRecord.append("null");
                }
                itineraryRecord.append(",");
                if (touristItinerary.getMission() != null) {
                    itineraryRecord.append(touristItinerary.getMission().getId());
                } else {
                    itineraryRecord.append("null");
                }
                writer.println(itineraryRecord.toString());
            }
        }
        writer.flush();
        writer.close();
    }

    private static void writeTourists(List<Tourist> tourists) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/tourists.csv"));
        writer.println("H,NAME,LOCATION");
        for (Tourist tourist : tourists) {
            writer.println("D," + tourist.getName() + "," + tourist.getLocation());
        }
        writer.flush();
        writer.close();
    }

    private static void writeFlights(List<Flight> flights) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File("database/flights.csv"));
        writer.println("H,ID,ORIGIN,DESTINATION,FLYBY,CAPACITY");
        for (Flight flight : flights) {
            writer.println("D," + flight.getId() + "," + flight.getOrigin() + "," + flight
                    .getDestination() + "," + flight.getFlyby() + "," + flight.getCapacity());
        }
        writer.flush();
        writer.close();
    }
}
