package org.example;

import org.example.dto.Character;
import org.example.service.CsvService;

import java.util.List;
import java.util.Map;

public class Main {

    private static final String CSV_FILE_PATH = "src/characters.csv";

    public static void main(String[] args) {
        CsvService csvService = new CsvService();
        MainTask mainTask = new MainTask();

        List<Character> characters = csvService.readCsvFile(CSV_FILE_PATH);
        Map<String, Map<String, Integer>> grouped = mainTask.toMap(characters);
        System.out.println(grouped);

        mainTask.writeToFile(characters);

        Character newChar = new Character(new String[]{
                "999", "Test Rick", "Alive", "Human", "", "Male",
                "Earth", "Citadel", "2024-01-01T00:00:00.000Z"
        });

        System.out.println("create: " + csvService.createCharacter(newChar, CSV_FILE_PATH));
        System.out.println("update: " + csvService.updateCharacter("999", newChar, CSV_FILE_PATH));
        System.out.println("delete: " + csvService.deleteCharacter("999", CSV_FILE_PATH));
    }
}