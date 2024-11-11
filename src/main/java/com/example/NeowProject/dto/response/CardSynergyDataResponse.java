package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CardSynergyDataResponse {
    @JsonProperty("card1_id")
    private long card1Id;

    @JsonProperty("card2_id")
    private long card2Id;

    @JsonProperty("synergy_value")
    private Double synergyValue;
}
