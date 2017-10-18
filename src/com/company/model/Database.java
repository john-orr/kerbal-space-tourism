package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Database {

    List<Tourist> tourists;

    public Database(List<Tourist> tourists) {
        this.tourists = tourists;
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void addTourist(Tourist tourist) {
        if (this.tourists == null) {
            this.tourists = new ArrayList<>();
        }
        this.tourists.add(tourist);
    }

    public String printTourists() {
        String nameColumnFormat = "%-10s";
        StringBuilder output =
                new StringBuilder("TOURISTS\n").append(String.format(nameColumnFormat, "NAME")).append("LOCATION\n");
        for (Tourist tourist : tourists) {
            output.append(String.format(nameColumnFormat, tourist.getName())).append(tourist.getLocation()).append("\n");
        }
        return output.toString();
    }

    public String print() {
        return printTourists();
    }
}
