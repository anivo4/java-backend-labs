package org.example;


import org.example.dto.Character;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainTask {

    private final String OUT_CSV_FILE_PATH = "src/outCharacters.csv";

    public Map<String, Map<String, Integer>> toMap(List<Character> characters) {
        return characters.stream()
                .collect(Collectors.groupingBy(
                        Character::getSpecies,
                        Collectors.groupingBy(
                                Character::getStatus,
                                Collectors.summingInt(c -> 1)
                        )
                ));
    }

    // Запись результата в CSV-файл
    public void writeToFile(List<Character> characters) {
        Map<String, Map<String, Integer>> result = toMap(characters);

        try (PrintWriter out = new PrintWriter(new FileWriter(OUT_CSV_FILE_PATH))) {
            out.println("species,status,count");

            // Обход вложенной структуры
            for (Map.Entry<String, Map<String, Integer>> speciesEntry : result.entrySet()) {
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

    // Экранирование значений с запятыми / кавычками / переносами
    private String escape(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}