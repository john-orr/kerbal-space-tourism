package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Database {

    List<Tourist> tourists;

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void addTourist(Tourist tourist) {
        if (this.tourists == null) {
            this.tourists = new ArrayList<>();
        }
        this.tourists.add(tourist);
    }
}
