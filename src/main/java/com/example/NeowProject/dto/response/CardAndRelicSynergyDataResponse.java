package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardAndRelicSynergyDataResponse {
    @JsonProperty("card_id")
    private long cardId;

    @JsonProperty("relic_id")
    private long relicId;

    @JsonProperty("synergy_value")
    private Double synergyValue;
}
