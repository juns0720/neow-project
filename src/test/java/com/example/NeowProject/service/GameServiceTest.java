package com.example.NeowProject.service;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.repository.GameRepository;
import com.example.NeowProject.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class GameServiceTest {
    @Autowired
    private GameService gameService;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void update(){
    Member member = new Member();
    member.setName("um");
    memberRepository.save(member);

    Game game = createGame(member, "1352352", true, 50, LocalDateTime.now(), "me", 599, 10, 1500, CharacterType.IRONCLAD);
    gameRepository.save(game);




    gameService.updateBestRecord(member);

    List<BestRecord> bestRecords = gameService.findBestRecordsByMember(member);



        for (BestRecord bestRecord : bestRecords) {
            System.out.println("캐릭터: " + bestRecord.getCharacterType());
            System.out.println("최소 시간: " + bestRecord.getMinTime());
            System.out.println("최고 점수: " + bestRecord.getBestScore());
            System.out.println("승률: " + bestRecord.getWinRate());
            System.out.println("-----------------------------------------");
        }

    }



     static Game createGame(Member member, String UUID, boolean isVictory, int finalFloor, LocalDateTime localTime, String defeatedBy, int playTime, int ascention, int score, CharacterType characterType) {
        Game game = new Game();

        game.setMember(member);
        game.setGameUUID(UUID);
        game.setVictory(isVictory);
        game.setFinalFloor(finalFloor);
        game.setLocalTime(localTime);
        game.setDefeatedBy(defeatedBy);
        LocalTime localplayTime = convertPlaytimeToTime(playTime);
        game.setPlayTime(localplayTime);
        game.setAscension(ascention);
        game.setScore(score);
        game.setCharacterType(characterType);

        return game;
     }
    public static LocalTime convertPlaytimeToTime(int playtimeInSeconds) {
        return LocalTime.ofSecondOfDay(playtimeInSeconds);
    }
}