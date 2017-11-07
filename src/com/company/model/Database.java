package com.company.model;

import java.io.FileNotFoundException;
import java.util.*;

public class Database {

    private List<Tourist> tourists;
    private List<Flight> flights;
    private List<Mission> missions;
    private List<Vessel> vessels;
    private static final String COLUMN_FORMAT = "%-15s| ";

    public Database(List<Tourist> tourists, List<Flight> flights, List<Vessel> vessels) {
        this.tourists = tourists;
        this.flights = flights;
        this.missions = new ArrayList<>();
        this.vessels = vessels;
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public List<Tourist> getFlaggedTourists() {
        List<Tourist> flaggedTourists = new ArrayList<>();
        for (Tourist tourist : tourists) {
            if (tourist.isFlagged()) {
                flaggedTourists.add(tourist);
            }
        }
        return flaggedTourists;
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
        if (this.tourists.contains(tourist)) {
            System.out.println("Tourist already exists");
            return;
        }
        this.tourists.add(tourist);
        Collections.sort(tourists);
    }

    public void removeTourist(Tourist tourist) {
        this.tourists.remove(tourist);
    }

    public boolean printTourists() {
        if (tourists.isEmpty()) {
            System.out.println("There are no tourists");
            return false;
        }
        StringBuilder output = new StringBuilder("TOURISTS\n")
                .append(tableCell("NAME"))
                .append(tableCell("LOCATION")).append("\n");
        for (Tourist tourist : tourists) {
            output.append(tableCell(tourist.getName()))
                    .append(tableCell(tourist.getLocation())).append("\n");
        }
        System.out.println(output.toString());
        return true;
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
        if (this.flights.contains(newFlight)) {
            System.out.println("Flight already exists");
            return;
        }
        newFlight.setKey(keyGen(flights));
        this.flights.add(newFlight);
        Collections.sort(flights);
    }

    public void printFlights() {
        printFlights(null);
    }

    private List<String> printFlights(List<String> origins) {
        List<String> flightKeys = new ArrayList<>();
        StringBuilder output = new StringBuilder("FLIGHTS\n")
                .append(tableCell("KEY"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION"))
                .append(tableCell("FLYBY")).append("\n");
        for (Flight flight : flights) {
            if (origins == null || origins.contains(flight.getOrigin())) {
                output.append(tableCell(flight.getKey()))
                        .append(tableCell(flight.getOrigin()))
                        .append(tableCell(flight.getDestination()))
                        .append(tableCell(flight.getFlyby())).append("\n");
                flightKeys.add(flight.getKey());
            }
        }
        if (!flightKeys.isEmpty()) {
            System.out.println(output.toString());
        }
        return flightKeys;
    }

    public void printFlightBookings(boolean includePrerequisites) {
        StringBuilder output = new StringBuilder("FLIGHTS\n")
                .append(tableCell("KEY"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION"))
                .append(tableCell("FLYBY"))
                .append(tableCell("CAPACITY"))
                .append(tableCell("NUM_BOOKINGS"))
                .append("CUSTOMERS").append("\n");
        for (Flight flight : flights) {
            List<TouristItinerary> bookingsWithoutMissions =
                    flight.getBookingsWithoutMissions(includePrerequisites);
            output.append(tableCell(flight.getKey()))
                    .append(tableCell(flight.getOrigin()))
                    .append(tableCell(flight.getDestination()))
                    .append(tableCell(flight.getFlyby()))
                    .append(tableCell(flight.getCapacity()))
                    .append(tableCell(bookingsWithoutMissions.size()));
            for (TouristItinerary customerItinerary : bookingsWithoutMissions) {
                output.append(customerItinerary.getTourist().getName()).append("\t");
            }
            output.append("\n");
        }
        System.out.println(output.toString());
    }

    private String tableCell(Object content) {
        return String.format(COLUMN_FORMAT, content);
    }

    public void insertItinerary(Tourist itineraryTourist, Flight flight, TouristItinerary prerequisite) {
        Set<TouristItinerary> touristItineraries = new HashSet<>();
        for (Tourist tourist : tourists) {
            touristItineraries.addAll(tourist.getItinerary());
        }
        String key = keyGen(touristItineraries);
        TouristItinerary touristItinerary = new TouristItinerary(key, itineraryTourist, flight, prerequisite);
        itineraryTourist.addToItinerary(touristItinerary);
        flight.addCustomerItinerary(touristItinerary);
    }

    public void printTouristItinerary(Tourist tourist) {
        if (tourist.getItinerary().isEmpty()) {
            System.out.println(tourist.getName() + "'s itinerary is empty");
            return;
        }
        StringBuilder output = new StringBuilder("ITINERARY\n")
                .append(tableCell("I.KEY"))
                .append(tableCell("NAME"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION"))
                .append(tableCell("FLYBY"))
                .append(tableCell("PREREQUISITE")).append("\n");
        for (TouristItinerary touristItinerary : tourist.getItinerary()) {
            output.append(tableCell(touristItinerary.getKey()))
                    .append(tableCell(tourist.getName()))
                    .append(tableCell(touristItinerary.getFlight().getOrigin()))
                    .append(tableCell(touristItinerary.getFlight().getDestination()))
                    .append(tableCell(touristItinerary.getFlight().getFlyby()));
            if (touristItinerary.getPrerequisite() != null) {
                output.append(tableCell(touristItinerary.getPrerequisite().getKey()));
            } else {
                output.append(tableCell("null"));
            }
            output.append("\n");
        }
        System.out.println(output.toString());
    }

    public List<String> printAvailableFlights(Tourist tourist, TouristItinerary prerequisite) {
        List<String> origins = new ArrayList<>();
        if (prerequisite == null) {
            origins.add("KERBIN");
            if (!"KERBIN".equals(tourist.getLocation())) {
                origins.add(tourist.getLocation());
            }
        } else {
            origins.add(prerequisite.getFlight().getDestination());
        }
        return printFlights(origins);
    }

    public Mission findMission(String missionKey) {
        for (Mission mission : missions) {
            if (mission.getKey().equals(missionKey)) {
                return mission;
            }
        }
        return null;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void insertMission(Mission newMission) {
        newMission.setKey(keyGen(missions));
        addMission(newMission);
    }

    public void addMission(Mission mission) {
        this.missions.add(mission);
        Collections.sort(missions);
    }

    public boolean printMissions() {
        if (missions.isEmpty()) {
            System.out.println("There are no missions");
            return false;
        }
        StringBuilder output = new StringBuilder("MISSIONS\n")
                .append(tableCell("KEY"))
                .append(tableCell("ORIGIN"))
                .append(tableCell("DESTINATION"))
                .append(tableCell("FLYBY"))
                .append(tableCell("VESSEL"))
                .append(tableCell("STATUS"))
                .append("PASSENGERS").append("\n");
        for (Mission mission : missions) {
            output.append(tableCell(mission.getKey()))
                    .append(tableCell(mission.getFlight().getOrigin()))
                    .append(tableCell(mission.getFlight().getDestination()))
                    .append(tableCell(mission.getFlight().getFlyby()))
                    .append(tableCell(mission.getVessel().getName()))
                    .append(tableCell(mission.getStatus()));
            for (TouristItinerary passengerItinerary : mission.getPassengerItineraries()) {
                output.append(passengerItinerary.getTourist().getName()).append("\t");
            }
            output.append("\n");
        }
        System.out.println(output.toString());
        return true;
    }

    public void completeMission(Mission mission) {
        this.missions.remove(mission);
        removeItineraries(mission.getPassengerItineraries());
        mission.deletePassengerItineraries();
        updateVesselLocation(mission.getVessel(), mission.getFlight().getDestination());
    }

    private void updateVesselLocation(Vessel vessel, String location) {
        if (location.equals("KERBIN")) {
            this.vessels.remove(vessel);
        } else {
            vessel.setLocation(location);
        }
    }

    public void cancelMission(Mission mission) {
        this.missions.remove(mission);
        for (TouristItinerary touristItinerary : mission.getPassengerItineraries()) {
            touristItinerary.setMission(null);
            touristItinerary.getTourist().setLocation(touristItinerary.getFlight().getOrigin());
        }
        mission.deletePassengerItineraries();
        updateVesselLocation(mission.getVessel(), mission.getFlight().getOrigin());
    }

    private void removeItineraries(List<TouristItinerary> touristItineraries) {
        for (TouristItinerary touristItinerary : touristItineraries) {
            Tourist tourist = touristItinerary.getTourist();
            tourist.removeFromItinerary(touristItinerary, false);
            tourist.setLocation(touristItinerary.getFlight().getDestination());
            if (tourist.getItinerary().isEmpty()) {
                tourist.flagForDelete();
            }
            touristItinerary.getFlight().removeCustomerItinerary(touristItinerary);
            touristItinerary.setMission(null);
        }
    }

    private String keyGen(Collection<? extends Entity> entities) {
        int keyGen = 0;
        for (Entity entity : entities) {
            int entityKey = Integer.parseInt(entity.getKey());
            if (entityKey > keyGen) {
                keyGen = entityKey;
            }
        }
        return String.format("%03d", keyGen + 1);
    }

    public List<Vessel> getVessels() {
        return vessels;
    }

    public Vessel findVessel(String name) {
        for (Vessel vessel : vessels) {
            if (vessel.getName().equals(name)) {
                return vessel;
            }
        }
        return null;
    }

    public List<Vessel> getAvailableVessels(Flight flight) {
        List<Vessel> availableVessels = new ArrayList<>();
        for (Vessel vessel : vessels) {
            if (flight.getOrigin().equals(vessel.getLocation())
                    && vessel.getCapacity() == flight.getCapacity()) {
                availableVessels.add(vessel);
            }
        }
        return availableVessels;
    }

    public List<String> printAvailableVessels(Flight flight) {
        List<String> availableVessels = new ArrayList<>();
        StringBuilder output = new StringBuilder("AVAILABLE VESSELS\n");
        for (Vessel vessel : getAvailableVessels(flight)) {
            availableVessels.add(vessel.getName());
            output.append(vessel.getName()).append("\n");
        }
        if (!availableVessels.isEmpty()) {
            System.out.println(output.toString());
        }
        return availableVessels;
    }

    public boolean createVessel(Vessel vessel) {
        if (vessel == null) {
            return true;
        }
        if (vessels.contains(vessel)) {
            System.out.println("Vessel already exists");
            return false;
        }
        vessels.add(vessel);
        return true;
    }
}
