package com.example.NeowProject.service;

import com.example.NeowProject.domain.*;
import com.example.NeowProject.dto.response.*;
import com.example.NeowProject.exception.CustomException;
import com.example.NeowProject.exception.ErrorCode;
import com.example.NeowProject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticsService {
    private final GameRepository gameRepository;
    private final FinalCardRepository finalCardRepository;
    private final FinalRelicRepository finalRelicRepository;
    private final CardRepository cardRepository;
    private final SelectedCardRewordRepository selectedCardRewordRepository;
    private final RelicRepository relicRepository;
    private final SelectBossRelicRepository selectBossRelicRepository;
    private final EnemyRepository enemyRepository;
    private final CardAndCardSynergyRepository cardAndCardSynergyRepository;
    private final CardAndRelicSynergyRepository cardAndRelicSynergyRepository;

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

            CardDataResponse response = new CardDataResponse(card.getId(), card.getName(), totalPickedRate, totalWinRate, actPickedRates);
            cardDataResponses.add(response);
        }

        return cardDataResponses;
    }

    public RelicDataResponse getRelicData() {
        List<RelicDataResponse.RelicDto> bossRelics = new ArrayList<>();
        List<RelicDataResponse.RelicWinRateDto> otherRelics = new ArrayList<>();

        List<Relic> bossRelicList = relicRepository.findRelicsByRelicType(RelicType.BOSS);
        for (Relic relic : bossRelicList) {
            long totalPicked = selectBossRelicRepository.countByRelic(relic);
            long totalSelected = selectBossRelicRepository.countByRelicAndSelectTrue(relic);
            double pickRate = totalPicked == 0 ? 0.0 : (double) totalSelected / totalPicked * 100;

            long totalRelicCount = finalRelicRepository.countByRelic(relic);
            long victoryCount = finalRelicRepository.countByRelicAndGameVictoryTrue(relic);
            double winRate = totalRelicCount == 0 ? 0.0 : (double) victoryCount / totalRelicCount * 100;

            bossRelics.add(new RelicDataResponse.RelicDto(relic.getId(), relic.getName(), winRate, pickRate));
        }

        // Other Relics
        List<Relic> otherRelicList = relicRepository.findRelicsByRelicTypeNot(RelicType.BOSS);
        for (Relic relic : otherRelicList) {
            long totalRelicCount = finalRelicRepository.countByRelic(relic);
            long victoryCount = finalRelicRepository.countByRelicAndGameVictoryTrue(relic);
            double winRate = totalRelicCount == 0 ? 0.0 : (double) victoryCount / totalRelicCount * 100;

            otherRelics.add(new RelicDataResponse.RelicWinRateDto(relic.getId(), relic.getName(), winRate));
        }

        return new RelicDataResponse(bossRelics, otherRelics);
    }

    public List<EnemyDataResponse> getEnemyData() {
        List<EnemyDataResponse> enemyDataResponses = new ArrayList<>();

        long totalDefeats = gameRepository.countByVictoryFalse();

        List<Enemy> enemies = enemyRepository.findAll();
        for (Enemy enemy : enemies) {
            long defeatedByEnemyCount = gameRepository.countByDefeatedByAndVictoryFalse(enemy.getName());
            double deathRate = totalDefeats == 0 ? 0.0 : (double) defeatedByEnemyCount / totalDefeats * 100;

            enemyDataResponses.add(new EnemyDataResponse(enemy.getName(), deathRate));
        }

        return enemyDataResponses;
    }

    public CardSynergyDataResponse getCardSynergyData(Long cardId1, Long cardId2) {
        Card card1 = cardRepository.findById(cardId1).orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        Card card2 = cardRepository.findById(cardId2).orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));

        Optional<CardAndCardSynergy> cardAndCardSynergy  = cardAndCardSynergyRepository.findByCard1AndCard2(card1, card2);
        Double synergyValue = cardAndCardSynergy.map(CardAndCardSynergy::getSynergy).orElse(null);

        return new CardSynergyDataResponse(card1.getId(), card2.getId(), synergyValue);
    }

    public CardAndRelicSynergyDataResponse getCardAndRelicSynergyData(Long cardId, Long relicId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new CustomException(ErrorCode.CARD_NOT_FOUND));
        Relic relic = relicRepository.findById(relicId).orElseThrow(() -> new CustomException(ErrorCode.RELIC_NOT_FOUND));

        Optional<CardAndRelicSynergy> cardAndRelicSynergy = cardAndRelicSynergyRepository.findByCardAndRelic(card, relic);
        Double synergyValue = cardAndRelicSynergy.map(CardAndRelicSynergy::getSynergy).orElse(null);

        return new CardAndRelicSynergyDataResponse(card.getId(), relic.getId(), synergyValue);
    }
}
