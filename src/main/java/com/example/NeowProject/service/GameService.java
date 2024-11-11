package com.example.NeowProject.service;

import com.example.NeowProject.domain.Member;
import com.example.NeowProject.dto.response.PlayRecordsResponse;
import com.example.NeowProject.repository.FinalCardRepository;
import com.example.NeowProject.repository.FinalRelicRepository;
import com.example.NeowProject.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final FinalCardRepository finalCardRepository;
    private final FinalRelicRepository finalRelicRepository;

    @Transactional(readOnly = true)
    public PlayRecordsResponse getPlayRecords(Member member) {
        List<PlayRecordsResponse.GameRecord> records = gameRepository.findGamesByMember(member).stream()
                .map(game -> {
                    List<String> finalCards = finalCardRepository.findByGame(game).stream()
                            .map(finalCard -> finalCard.getCard().getName())
                            .toList();

                    List<String> finalRelics = finalRelicRepository.findByGame(game).stream()
                            .map(finalRelic -> finalRelic.getRelic().getName())
                            .toList();

                    return new PlayRecordsResponse.GameRecord(
                            game.getGameUUID(),
                            game.isVictory(),
                            game.getFinalFloor(),
                            game.getPlayTime(),
                            game.getAscension(),
                            game.getScore(),
                            game.getCharacterType().name(),
                            finalCards,
                            finalRelics
                    );
                })
                .collect(Collectors.toList());

        return new PlayRecordsResponse(records);
    }
}
