package com.company.model;

import com.company.Persister;

import java.io.FileNotFoundException;
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

    public void insertTourist(Tourist tourist) throws FileNotFoundException {
        if (this.tourists == null) {
            this.tourists = new ArrayList<>();
        }
        this.tourists.add(tourist);
        Persister.write(this);
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
