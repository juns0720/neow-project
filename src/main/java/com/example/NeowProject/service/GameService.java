package com.example.NeowProject.service;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.dto.response.PlayRecordsResponse;
import com.example.NeowProject.repository.BestRecordRepository;
import com.example.NeowProject.repository.FinalCardRepository;
import com.example.NeowProject.repository.FinalRelicRepository;
import com.example.NeowProject.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final FinalCardRepository finalCardRepository;
    private final FinalRelicRepository finalRelicRepository;
    private final BestRecordRepository bestRecordRepository;

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

    //BestRecord 기능
    @Transactional
    public Long saveBestRecord(BestRecord bestRecord) {
        bestRecordRepository.save(bestRecord);
        return bestRecord.getId();
    }

    public BestRecord findBestRecordByMemberAndCharacterType(Member member, CharacterType characterType) {
        return bestRecordRepository.findOneByMemberAndCharacterType(member, characterType);
    }

    public List<BestRecord> findBestRecordsByMember(Member member) {
        return bestRecordRepository.findAllByMember(member);
    }

    @Transactional
    public void updateBestRecord(Member member, Game newGame) {

        // BestScore, MinTime, Ascension, win, lose
        List<BestRecord> bestRecords = bestRecordRepository.findBestRecordsForUpdate(member, newGame.getCharacterType());

        for (BestRecord bestRecord : bestRecords) {
            if (newGame.getScore() > bestRecord.getBestScore())
                bestRecord.setBestScore(newGame.getScore());
            if (newGame.getPlayTime().toSecondOfDay() < bestRecord.getMinTime())
                bestRecord.setMinTime(newGame.getPlayTime().toSecondOfDay());
            if (newGame.getAscension() > bestRecord.getMaxAscension())
                bestRecord.setMaxAscension(newGame.getAscension());
             if (newGame.isVictory())
                 bestRecord.setWin(bestRecord.getWin() + 1);
             else
                 bestRecord.setLose(bestRecord.getLose() + 1);

        }




    }


}
