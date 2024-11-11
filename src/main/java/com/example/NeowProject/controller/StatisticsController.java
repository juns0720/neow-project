package com.example.NeowProject.controller;

import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Color;
import com.example.NeowProject.dto.response.*;
import com.example.NeowProject.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/global")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/character")
    public ResponseEntity<?> getCharacterData(@RequestParam("character") String character) {
        CharacterDataResponse characterData = statisticsService.getCharacterData(CharacterType.valueOf(character));
        return ResponseEntity.status(HttpStatus.OK).body(characterData);
    }

    @GetMapping("/card")
    public ResponseEntity<?> getCardData(@RequestParam("color") String color) {
        List<CardDataResponse> cardDataByColor = statisticsService.getCardDataByColor(Color.valueOf(color));
        return ResponseEntity.status(HttpStatus.OK).body(cardDataByColor);
    }

    @GetMapping("/relic")
    public ResponseEntity<?> getRelicData() {
        RelicDataResponse relicData = statisticsService.getRelicData();
        return ResponseEntity.status(HttpStatus.OK).body(relicData);
    }

    @GetMapping("/death-rate")
    public ResponseEntity<?> getDeathData() {
        List<EnemyDataResponse> enemyData = statisticsService.getEnemyData();
        return ResponseEntity.status(HttpStatus.OK).body(enemyData);
    }

    @GetMapping("/synergy")
    public ResponseEntity<?> getCardSynergy(@RequestParam("card_id1") Long card1Id, @RequestParam("card_id2") Long card2Id) {
        CardSynergyDataResponse cardSynergyData = statisticsService.getCardSynergyData(card1Id, card2Id);
        return ResponseEntity.status(HttpStatus.OK).body(cardSynergyData);
    }
}
