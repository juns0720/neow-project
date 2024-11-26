package com.example.NeowProject.dto.response;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
public class BestRecordResponse {

    private String name;

    @JsonProperty("max_ascension")
    private int maxAscension;

    @JsonProperty("win_rate")
    private double winRate;

    private int win;

    private int lose;

    @JsonProperty("min_time")
    private int minTime;


    @JsonProperty("best_score")
    private int bestScore;

    @JsonProperty("character_type")
    private CharacterType characterType;

    public BestRecordResponse(BestRecord bestRecord){

        this.name = bestRecord.getMember().getName();
        this.maxAscension = bestRecord.getMaxAscension();
        this.win = bestRecord.getWin();
        this.lose = bestRecord.getLose();
        this.winRate =  ((double) bestRecord.getWin() * 100) / (bestRecord.getWin() + bestRecord.getLose());
        this.minTime = bestRecord.getMinTime();
        this.bestScore = bestRecord.getBestScore();
        this.characterType = bestRecord.getCharacterType();
    }








}
