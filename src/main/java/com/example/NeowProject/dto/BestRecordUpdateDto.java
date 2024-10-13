package com.example.NeowProject.dto;

import com.example.NeowProject.domain.CharacterType;
import lombok.Data;

@Data
public class BestRecordUpdateDto {
    private Integer maxAscension;

    private Double winRate;

    private Integer maxStreak;

    private Integer minTime;

    private Integer bestScore;

    private CharacterType characterType;

    public BestRecordUpdateDto(Integer maxAscension, Double winRate, Integer maxStreak, Integer minTime, Integer bestScore, CharacterType characterType) {
        this.maxAscension = maxAscension;
        this.winRate = winRate;
        this.maxStreak = maxStreak;
        this.minTime = minTime;
        this.bestScore = bestScore;
        this.characterType = characterType;
    }

}
