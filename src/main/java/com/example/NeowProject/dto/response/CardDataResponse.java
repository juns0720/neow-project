package com.example.NeowProject.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CardDataResponse {
    @JsonProperty("card_id")
    private Long cardId;

    @JsonProperty("total_picked_rate")
    private double totalPickedRate;

    @JsonProperty("total_win_rate")
    private double totalWinRate;

    @JsonProperty("picked_by_acts")
    private List<ActPickedRate> pickedByActs;

    @AllArgsConstructor
    @Getter
    public static class ActPickedRate {
        @JsonProperty("act")
        private int act;

        @JsonProperty("picked_rate")
        private double pickedRate;
    }
}
