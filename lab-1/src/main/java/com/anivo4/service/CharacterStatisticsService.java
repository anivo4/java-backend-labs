package com.anivo4.service;

import com.anivo4.dto.Character;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterStatisticsService {

    public Map<String, Map<String, Integer>> groupBySpeciesAndStatus(List<Character> characters) {
        return characters.stream()
                .collect(Collectors.groupingBy(
                        Character::getSpecies,
                        Collectors.groupingBy(
                                Character::getStatus,
                                Collectors.summingInt(c -> 1)
                        )
                ));
    }
}