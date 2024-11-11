package com.example.NeowProject.service;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Color;
import com.example.NeowProject.dto.response.CardDataResponse;
import com.example.NeowProject.dto.response.CharacterDataResponse;
import com.example.NeowProject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {
    private final GameRepository gameRepository;
    private final FinalCardRepository finalCardRepository;
    private final FinalRelicRepository finalRelicRepository;
    private final CardRepository cardRepository;
    private final SelectedCardRewordRepository selectedCardRewordRepository;

    public CharacterDataResponse getCharacterData(CharacterType characterType) {
        long totalGames = gameRepository.count();

        long characterTotalGames = gameRepository.countByCharacterType(characterType);
        double pickRate = totalGames == 0 ? 0.0 : (double) characterTotalGames / totalGames * 100;

        List<CharacterDataResponse.winRateDto> winRates = new ArrayList<>();

        for (int ascension = 0; ascension <= 20; ascension++) {
            long ascensionTotalGames = gameRepository.countByCharacterTypeAndAscension(characterType, ascension);

            if (ascensionTotalGames == 0) {
                winRates.add(new CharacterDataResponse.winRateDto(ascension, 0.0));
                continue;
            }

            long wins = gameRepository.countByCharacterTypeAndAscensionAndVictoryTrue(characterType, ascension);
            double winRate = (double) wins / ascensionTotalGames * 100;
            winRates.add(new CharacterDataResponse.winRateDto(ascension, winRate));
        }

        return new CharacterDataResponse(characterType, pickRate, winRates);
    }

    public List<CardDataResponse> getCardDataByColor(Color color) {
        List<Card> cards = cardRepository.findCardsByColor(color);
        List<CardDataResponse> cardDataResponses = new ArrayList<>();

        for (Card card : cards) {
            long totalShowed = selectedCardRewordRepository.countByCard(card);
            long totalSelected = selectedCardRewordRepository.countByCardAndSelectTrue(card);
            double totalPickedRate = totalShowed == 0 ? 0.0 : (double) totalSelected / totalShowed * 100;

            long totalFinalCardCount = finalCardRepository.countByCard(card);
            long totalVictoryCount = finalCardRepository.countByCardAndGameVictoryTrue(card);
            double totalWinRate = totalFinalCardCount == 0 ? 0.0 : (double) totalVictoryCount / totalFinalCardCount * 100;

            // Act1 picked rate
            long act1Total = selectedCardRewordRepository.countByCardAndFloorLessThanEqual(card, 17);
            long act1Selected = selectedCardRewordRepository.countByCardAndFloorLessThanEqualAndSelectTrue(card, 17);
            double act1PickedRate = act1Total == 0 ? 0.0 : (double) act1Selected / act1Total * 100;

            // Act2 picked rate
            long act2Total = selectedCardRewordRepository.countByCardAndFloorBetween(card, 18, 34);
            long act2Selected = selectedCardRewordRepository.countByCardAndFloorBetweenAndSelectTrue(card, 18, 34);
            double act2PickedRate = act2Total == 0 ? 0.0 : (double) act2Selected / act2Total * 100;

            // Act3 picked rate
            long act3Total = selectedCardRewordRepository.countByCardAndFloorGreaterThan(card, 34);
            long act3Selected = selectedCardRewordRepository.countByCardAndFloorGreaterThanAndSelectTrue(card, 34);
            double act3PickedRate = act3Total == 0 ? 0.0 : (double) act3Selected / act3Total * 100;

            List<CardDataResponse.ActPickedRate> actPickedRates = List.of(
                    new CardDataResponse.ActPickedRate(1, act1PickedRate),
                    new CardDataResponse.ActPickedRate(2, act2PickedRate),
                    new CardDataResponse.ActPickedRate(3, act3PickedRate)
            );

            CardDataResponse response = new CardDataResponse(card.getId(), totalPickedRate, totalWinRate, actPickedRates);
            cardDataResponses.add(response);
        }

        return cardDataResponses;
    }
}
