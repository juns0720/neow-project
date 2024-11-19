package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EnemyDataResponse {
    @JsonProperty("enemy")
    private String enemy;

    @JsonProperty("death_rate")
    private double deathRate;
}
