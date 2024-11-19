package com.example.NeowProject.dto.response;

import com.example.NeowProject.domain.CharacterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CharacterDataResponse {
    @JsonProperty("character_type")
    private CharacterType characterType;

    @JsonProperty("pick_rate")
    private double pickRate;

    @JsonProperty("win_rates")
    private List<winRateDto> winRates;

    @AllArgsConstructor
    @Getter
    public static class winRateDto {
        private int ascension;

        @JsonProperty("win_rate")
        private double winRate;
    }
}
