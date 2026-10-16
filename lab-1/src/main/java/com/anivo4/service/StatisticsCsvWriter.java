package com.anivo4.service;

 import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StatisticsCsvWriter {

    public void write(String filePath, Map<String, Map<String, Integer>> statistics) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("species,status,count");

            for (Map.Entry<String, Map<String, Integer>> speciesEntry : statistics.entrySet()) {
                String species = speciesEntry.getKey();
                for (Map.Entry<String, Integer> statusEntry : speciesEntry.getValue().entrySet()) {
                    String status = statusEntry.getKey();
                    int count = statusEntry.getValue();
                    out.println(escape(species) + "," + escape(status) + "," + count);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
