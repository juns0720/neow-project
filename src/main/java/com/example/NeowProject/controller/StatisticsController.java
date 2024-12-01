package com.example.NeowProject.controller;

import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Color;
import com.example.NeowProject.dto.response.*;
import com.example.NeowProject.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/global")
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/character")
    public ResponseEntity<?> getCharacterData(@RequestParam(value = "character", required = false) String character) {
        if (character == null || character.isEmpty()) {
            // 모든 캐릭터에 대한 데이터를 반환
            List<CharacterDataResponse> characterDataList = Arrays.stream(CharacterType.values())
                    .map(statisticsService::getCharacterData)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(characterDataList);
        }
        else {
            CharacterDataResponse characterData = statisticsService.getCharacterData(CharacterType.valueOf(character));
            return ResponseEntity.status(HttpStatus.OK).body(characterData);
        }
    }

    @GetMapping("/card")
    public ResponseEntity<?> getCardData(@RequestParam(value = "color", required = false) String color) {
        List<CardDataResponse> cardDataResponses;

        if (color == null || color.isEmpty()) {
            // 모든 색상에 대한 데이터를 반환
            cardDataResponses = Arrays.stream(Color.values())
                    .flatMap(c -> statisticsService.getCardDataByColor(c).stream())
                    .collect(Collectors.toList());
        } else {
            // 특정 색상에 대한 데이터만 반환
            cardDataResponses = statisticsService.getCardDataByColor(Color.valueOf(color));
        }
        return ResponseEntity.status(HttpStatus.OK).body(cardDataResponses);
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

    @GetMapping("/card-synergy")
    public ResponseEntity<?> getCardSynergy(@RequestParam("card_id1") Long card1Id, @RequestParam("card_id2") Long card2Id) {
        CardSynergyDataResponse cardSynergyData = statisticsService.getCardSynergyData(card1Id, card2Id);
        return ResponseEntity.status(HttpStatus.OK).body(cardSynergyData);
    }

    @GetMapping("/synergy")
    public ResponseEntity<?> getCardAndRelicSynergy(@RequestParam("card_id") Long cardId, @RequestParam("relic_id") Long relicId) {
        CardAndRelicSynergyDataResponse cardAndRelicSynergyData = statisticsService.getCardAndRelicSynergyData(cardId, relicId);
        return ResponseEntity.status(HttpStatus.OK).body(cardAndRelicSynergyData);
    }
}
