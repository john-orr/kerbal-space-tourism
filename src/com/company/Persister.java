package com.company;

import com.company.model.*;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

class Persister {

    static void write(Database database, Statement statement) throws SQLException {
        clearTables(statement);
        insertTourists(statement, database.getTourists());
        insertFlights(statement, database.getFlights());
        insertVessels(statement, database.getVessels());
        insertMissions(statement, database.getMissions());
        insertTouristItinerary(statement, database.getTourists());
    }

    private static void clearTables(Statement statement) throws SQLException {
        statement.execute("UPDATE TOURIST_ITINERARY SET PREREQUISITE=null");
        statement.execute("DELETE FROM TOURIST_ITINERARY");
        statement.execute("DELETE FROM MISSION");
        statement.execute("DELETE FROM VESSEL");
        statement.execute("DELETE FROM FLIGHT");
        statement.execute("DELETE FROM TOURIST");
    }

    private static void insertVessels(Statement statement, List<Vessel> vessels) throws SQLException {
        if (!vessels.isEmpty()) {
            StringBuilder vesselInsert = new StringBuilder("INSERT INTO VESSEL VALUES ");
            for (int i = 0; i < vessels.size(); i++) {
                Vessel vessel = vessels.get(i);
                vesselInsert.append("('")
                        .append(vessel.getName())
                        .append("', '")
                        .append(vessel.getLocation())
                        .append("', ")
                        .append(vessel.getCapacity())
                        .append(")");
                if (i != vessels.size() - 1) {
                    vesselInsert.append(", ");
                }
            }
            statement.execute(vesselInsert.toString());
        }
    }

    private static void insertMissions(Statement statement, List<Mission> missions) throws SQLException {
        if (!missions.isEmpty()) {
            StringBuilder missionInsert = new StringBuilder("INSERT INTO MISSION VALUES ");
            for (int i = 0; i < missions.size(); i++) {
                Mission mission = missions.get(i);
                missionInsert.append("(")
                        .append(mission.getId())
                        .append(", ")
                        .append(mission.getFlight().getId())
                        .append(", '")
                        .append(mission.getVessel().getName())
                        .append("', '")
                        .append(mission.getStatus())
                        .append("')");
                if (i != missions.size() - 1) {
                    missionInsert.append(", ");
                }
            }
            statement.execute(missionInsert.toString());
        }
    }

    private static void insertTouristItinerary(Statement statement, List<Tourist> tourists) throws SQLException {
        boolean hasRecordsToInsert = false;
        StringBuilder touristItineraryInsert = new StringBuilder("INSERT INTO TOURIST_ITINERARY VALUES ");
        for (int i = 0; i < tourists.size(); i++) {
            Tourist tourist = tourists.get(i);
            for (int j = 0; j < tourist.getItinerary().size(); j++) {
                TouristItinerary touristItinerary = tourist.getItinerary().get(j);
                hasRecordsToInsert = true;
                touristItineraryInsert.append("(")
                        .append(touristItinerary.getId())
                        .append(", '")
                        .append(tourist.getName())
                        .append("', ")
                        .append(touristItinerary.getFlight().getId())
                        .append(", ");
                if (touristItinerary.getPrerequisite() != null) {
                    touristItineraryInsert.append(touristItinerary.getPrerequisite().getId());
                } else {
                    touristItineraryInsert.append("null");
                }
                touristItineraryInsert.append(", ");
                if (touristItinerary.getMission() != null) {
                    touristItineraryInsert.append(touristItinerary.getMission().getId());
                } else {
                    touristItineraryInsert.append("null");
                }
                touristItineraryInsert.append(")");
                if (j != tourist.getItinerary().size() - 1) {
                    touristItineraryInsert.append(", ");
                }
            }
            if (i != tourists.size() - 1) {
                touristItineraryInsert.append(", ");
            }
        }
        if (hasRecordsToInsert) {
            statement.execute(touristItineraryInsert.toString());
        }
    }

    private static void insertTourists(Statement statement, List<Tourist> tourists) throws SQLException {
        if (!tourists.isEmpty()) {
            StringBuilder touristInsert = new StringBuilder("INSERT INTO TOURIST VALUES ");
            for (int i = 0; i < tourists.size(); i++) {
                Tourist tourist = tourists.get(i);
                touristInsert.append("('")
                        .append(tourist.getName())
                        .append("', '")
                        .append(tourist.getLocation())
                        .append("')");
                if (i != tourists.size() - 1) {
                    touristInsert.append(", ");
                }
            }
            statement.execute(touristInsert.toString());
        }
    }

    private static void insertFlights(Statement statement, List<Flight> flights) throws SQLException {
        if (!flights.isEmpty()) {
            StringBuilder flightInsert = new StringBuilder("INSERT INTO FLIGHT VALUES ");
            for (int i = 0; i < flights.size(); i++) {
                Flight flight = flights.get(i);
                flightInsert.append("(")
                        .append(flight.getId())
                        .append(", '")
                        .append(flight.getOrigin())
                        .append("', '")
                        .append(flight.getDestination())
                        .append("', ");
                if (flight.getFlyby() == null) {
                    flightInsert.append("null");
                } else {
                    flightInsert.append("'").append(flight.getFlyby()).append("'");
                }
                flightInsert.append(", ")
                        .append(flight.getCapacity())
                        .append(")");
                if (i != flights.size() - 1) {
                    flightInsert.append(", ");
                }
            }
            statement.execute(flightInsert.toString());
        }
    }
}
