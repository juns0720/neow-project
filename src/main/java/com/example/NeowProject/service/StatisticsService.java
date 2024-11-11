package com.example.NeowProject.service;

import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.dto.response.CharacterDataResponse;
import com.example.NeowProject.repository.FinalCardRepository;
import com.example.NeowProject.repository.FinalRelicRepository;
import com.example.NeowProject.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
}
