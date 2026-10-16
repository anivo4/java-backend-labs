package org.example.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.example.dto.Character;
public class CsvService {
    public List<Character> readCsvFile(String csvFile) {
        List<Character> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(csvFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                if (Pattern.matches("\\d+", values[0])){
                    Character character = new Character(values);
                    data.add(character);
                }
            }

        }
        catch (FileNotFoundException e) {
            System.err.println("CSV file not found: " + e.getMessage());
            return new ArrayList<>();
        }

        return data;
    }
    public boolean createCharacter(Character character, String csvFile) {
        List<Character> existing = readCsvFile(csvFile);
        for (Character c : existing) {
            if (c.getId().equals(character.getId())) {
                System.err.println("Character with id=" + character.getId() + " already exists");
                return false;
            }
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(csvFile, true))) {
            out.println(toCsvLine(character));
            return true;
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
            return false;
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

    public void printCsvData(List<Character> data){
        for(int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
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
            System.err.println("CSV file not found: " + e.getMessage());
        }
    }
}