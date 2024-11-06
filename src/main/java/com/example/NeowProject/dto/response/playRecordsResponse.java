package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class playRecordsResponse {
    private List<GameRecord> records;

    @Getter
    @AllArgsConstructor
    public static class GameRecord {
        @JsonProperty("game_uuid")
        private String gameUuid;

        @JsonProperty("is_victory")
        private boolean isVictory;

        @JsonProperty("final_floor")
        private int finalFloor;

        @JsonProperty("play_time")
        private LocalTime playTime;

        @JsonProperty("ascension_level")
        private int ascensionLevel;

        private int score;
        private String character;

        @JsonProperty("final_cards")
        private List<String> finalCards;

        @JsonProperty("final_relics")
        private List<String> finalRelics;
    }
}
