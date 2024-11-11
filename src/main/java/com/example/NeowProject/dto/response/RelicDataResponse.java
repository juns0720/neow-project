package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RelicDataResponse {
    @JsonProperty("boss_relics")
    private List<RelicDto> bossRelics;

    @JsonProperty("other_relics")
    private List<RelicWinRateDto> otherRelics;

    @AllArgsConstructor
    @Getter
    public static class RelicDto {
        @JsonProperty("relic_id")
        private Long relicId;

        private String name;

        @JsonProperty("win_rate")
        private double winRate;

        @JsonProperty("pick_rate")
        private double pickRate;
    }

    @AllArgsConstructor
    @Getter
    public static class RelicWinRateDto {
        @JsonProperty("relic_id")
        private Long relicId;

        private String name;

        @JsonProperty("win_rate")
        private double winRate;
    }
}
