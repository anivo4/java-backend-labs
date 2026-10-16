package com.anivo4;

import com.anivo4.dto.Character;
import com.anivo4.service.CharacterStatisticsService;
import com.anivo4.service.CsvService;
import com.anivo4.service.StatisticsCsvWriter;

import java.util.List;
import java.util.Map;

public class Main {

    private static final String CSV_FILE_PATH = "lab-1/src/main/resources/characters.csv";
    private static final String OUT_CSV_FILE_PATH = "lab-1/src/main/resources/outCharacters.csv";

    public static void main(String[] args) {
        CsvService csvService = new CsvService();
        CharacterStatisticsService statisticsService = new CharacterStatisticsService();
        StatisticsCsvWriter writer = new StatisticsCsvWriter();

        List<Character> characters = csvService.readCsvFile(CSV_FILE_PATH);

        Map<String, Map<String, Integer>> grouped = statisticsService.groupBySpeciesAndStatus(characters);
        System.out.println(grouped);

        writer.write(OUT_CSV_FILE_PATH, grouped);
    }
}