package com.company;

import com.company.model.Database;
import com.company.model.Tourist;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Retriever {

    static Database read() throws IOException {
        List<Tourist> tourists = readTourists();
        return new Database(tourists);
    }

    private static List<Tourist> readTourists() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("database/tourists.csv")));
        String line;
        List<Tourist> tourists = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            try {
                String[] data = line.split(",", 3);
                if (data[0].equals("H")) {
                    continue;
                }
                tourists.add(new Tourist(data[1], data[2]));
            } catch (Exception e) {
                System.out.println("Error reading line " + line);
                throw e;
            }
        }
        return tourists;
    }
}
