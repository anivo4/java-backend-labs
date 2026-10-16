package com.anivo4.service;

import com.anivo4.dto.Character;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CsvService {

    public List<Character> readCsvFile(String csvFile) {
        List<Character> data = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(csvFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                if (Pattern.matches("\\d+", values[0])) {
                    Character character = new Character(values);
                    data.add(character);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found: " + csvFile, e);
        }

        return data;
    }

    public boolean createCharacter(Character character, String csvFile) {
        List<Character> existing = readCsvFile(csvFile);

        for (Character c : existing) {
            if (c.getId().equals(character.getId())) {
                return false;
            }
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(csvFile, true))) {
            out.println(toCsvLine(character));
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to CSV: " + csvFile, e);
        }
    }

    public boolean updateCharacter(String id, Character newCharacter, String csvFile) {
        List<Character> data = readCsvFile(csvFile);
        boolean updated = false;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId().equals(id)) {
                data.set(i, newCharacter);
                updated = true;
                break;
            }
        }

        if (updated) {
            rewriteCsv(data, csvFile);
        }

        return updated;
    }

    public boolean deleteCharacter(String id, String csvFile) {
        List<Character> data = readCsvFile(csvFile);
        boolean removed = data.removeIf(c -> c.getId().equals(id));

        if (removed) {
            rewriteCsv(data, csvFile);
        }

        return removed;
    }

    public void printCsvData(List<Character> data) {
        for (Character character : data) {
            System.out.println(character);
        }
    }

    private String toCsvLine(Character c) {
        return String.join(",",
                safe(c.getId()),
                safe(c.getName()),
                safe(c.getStatus()),
                safe(c.getSpecies()),
                safe(c.getType()),
                safe(c.getGender()),
                safe(c.getOriginName()),
                safe(c.getLocationName()),
                safe(c.getCreated())
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private void rewriteCsv(List<Character> data, String csvFile) {
        try (PrintWriter out = new PrintWriter(csvFile)) {
            for (Character c : data) {
                out.println(toCsvLine(c));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("CSV file not found: " + csvFile, e);
        }
    }
}