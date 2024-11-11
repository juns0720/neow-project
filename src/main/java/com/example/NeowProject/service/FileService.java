package com.example.NeowProject.service;

import com.example.NeowProject.domain.*;
import com.example.NeowProject.exception.CustomException;
import com.example.NeowProject.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.example.NeowProject.exception.ErrorCode.*;

@Service
public class FileService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameRepository gameRepository;
    private final EnemyRepository enemyRepository;
    private final BattleRepository battleRepository;
    private final CardRepository cardRepository;
    private final FinalCardRepository finalCardRepository;
    private final SelectedCardRewordRepository selectedCardRewordRepository;
    private final RelicRepository relicRepository;
    private final FinalRelicRepository finalRelicRepository;
    private final SelectBossRelicRepository selectBossRelicRepository;

    @Autowired
    public FileService(GameRepository gameRepository, EnemyRepository enemyRepository, BattleRepository battleRepository, CardRepository cardRepository, FinalCardRepository finalCardRepository, SelectedCardRewordRepository selectedCardRewordRepository, RelicRepository relicRepository, FinalRelicRepository finalRelicRepository, SelectBossRelicRepository selectBossRelicRepository) {
        this.gameRepository = gameRepository;
        this.enemyRepository = enemyRepository;
        this.battleRepository = battleRepository;
        this.cardRepository = cardRepository;
        this.finalCardRepository = finalCardRepository;
        this.selectedCardRewordRepository = selectedCardRewordRepository;
        this.relicRepository = relicRepository;
        this.finalRelicRepository = finalRelicRepository;
        this.selectBossRelicRepository = selectBossRelicRepository;
    }

    public String saveJsonFile(MultipartFile file) {

        try {
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

            String playId = jsonNode.get("play_id").asText();
            Path resourceDirectory = Paths.get("src", "main", "resources", "uploads");
            if (!Files.exists(resourceDirectory)) {
                Files.createDirectories(resourceDirectory); // 폴더가 없을 경우 생성
            }
            Path filePath = resourceDirectory.resolve(playId + ".json");

            Files.write(filePath, file.getBytes());

            return playId;
        } catch (IOException e) {
            throw new CustomException(FILE_SAVE_FAILED);
        }


    }

    public JsonNode loadJsonFile(String playId) {
        Path resourceDirectory = Paths.get("src", "main", "resources", "uploads");
        File file = resourceDirectory.resolve(playId + ".json").toFile();

        if (file.exists()) {
            try {
                return objectMapper.readTree(file);
            } catch (IOException e) {
                throw new CustomException(FILE_READ_FAILED);
            }
        } else {
            throw new CustomException(FILE_NOT_FOUND);
        }
    }

    @Transactional
    public void saveGameData(String play_id, Member member) {
        //game 데이터 저장
        JsonNode gameData = loadJsonFile(play_id);
        boolean isVictory = gameData.get("victory").asBoolean();
        int finalFloor = gameData.get("floor_reached").asInt();
        LocalDateTime localTime = parseLocalTime(gameData.get("local_time").asText());
        String defeatedBy = null;
        if(!isVictory) {
            defeatedBy = findCauseOfDeathByFloor(gameData, finalFloor);
        }
        LocalTime playTime = convertPlaytimeToTime(gameData.get("playtime").asInt());
        int ascension = gameData.get("ascension_level").asInt();
        int score = gameData.get("score").asInt();
        CharacterType characterType = convertStringToCharacterType(gameData.get("character_chosen").asText());

        Game game = new Game();
        game.setMember(member);
        game.setGameUUID(play_id);
        game.setVictory(isVictory);
        game.setFinalFloor(finalFloor);
        game.setLocalTime(localTime);
        game.setDefeatedBy(defeatedBy);
        game.setPlayTime(playTime);
        game.setAscension(ascension);
        game.setScore(score);
        game.setCharacterType(characterType);
        gameRepository.save(game);


        //전투 테이블 저장
        JsonNode damageTakenData = gameData.get("damage_taken");
        for (JsonNode battleNode : damageTakenData) {
            String enemyName = battleNode.get("enemies").asText();
            int turn = battleNode.get("turns").asInt();
            int damage = battleNode.get("damage").asInt();
            int floor = battleNode.get("floor").asInt();

            Enemy enemy = enemyRepository.findByName(enemyName);
            if (enemy == null) {
                throw new IllegalArgumentException("Enemy not found: " + enemyName);
            }

            Battle battle = new Battle();
            battle.setGame(game);
            battle.setEnemy(enemy);
            battle.setTurn(turn);
            battle.setDamage(damage);
            battle.setFloor(floor);
            battleRepository.save(battle);
        }

        //카드 최종 보유 저장
        JsonNode masterDeck = gameData.get("master_deck");
        Map<String, Integer> cardCountMap = new HashMap<>();
        for (JsonNode cardNode : masterDeck) {
            String cardName = cardNode.asText();
            cardCountMap.put(cardName, cardCountMap.getOrDefault(cardName, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : cardCountMap.entrySet()) {
            String cardName = entry.getKey();
            int count = entry.getValue();

            // 카드 이름으로 조회하여 Card 엔티티 찾기
            Card card = cardRepository.findByName(removeNumberCardName(cardName));
            if (card == null) {
                throw new IllegalArgumentException("Card not found: " + cardName);
            }

            // FinalCard 엔티티 생성 후 저장
            FinalCard finalCard = new FinalCard();
            finalCard.setGame(game);
            finalCard.setCard(card);
            finalCard.setCount(count);
            finalCardRepository.save(finalCard);
        }

        //카드 선택 저장
        JsonNode cardChoices = gameData.get("card_choices");
        for (JsonNode cardChoiceNode : cardChoices) {
            String pickedCardName = cardChoiceNode.get("picked").asText();
            int floor = cardChoiceNode.get("floor").asInt();

            if (!"SKIP".equalsIgnoreCase(pickedCardName)) {
                Card pickedCard = cardRepository.findByName(removeNumberCardName(pickedCardName));
                if (pickedCard == null) {
                    throw new IllegalArgumentException("Card not found: " + pickedCardName);
                }

                SelectBattleRewardCard selectedCard = new SelectBattleRewardCard();
                selectedCard.setGame(game);
                selectedCard.setCard(pickedCard);
                selectedCard.setFloor(floor);
                selectedCard.setSelect(true);
                selectedCardRewordRepository.save(selectedCard);
            }

            for (JsonNode notPickedNode : cardChoiceNode.get("not_picked")) {
                String notPickedCardName = notPickedNode.asText();
                Card notPickedCard = cardRepository.findByName(removeNumberCardName(notPickedCardName));
                if (notPickedCard == null) {
                    throw new IllegalArgumentException("Card not found: " + notPickedCardName);
                }

                SelectBattleRewardCard unselectedCard = new SelectBattleRewardCard();
                unselectedCard.setGame(game);
                unselectedCard.setCard(notPickedCard);
                unselectedCard.setFloor(floor);
                unselectedCard.setSelect(false);
                selectedCardRewordRepository.save(unselectedCard);
            }
        }

        //유물 최종 보유 저장
        JsonNode relics = gameData.get("relics");
        for (JsonNode relicNode : relics) {
            String relicName = relicNode.asText();
            Relic relic = relicRepository.findByName(relicName).orElseThrow(() -> new CustomException(RELIC_NOT_FOUND));

            FinalRelic finalRelic = new FinalRelic();
            finalRelic.setGame(game);
            finalRelic.setRelic(relic);
            finalRelicRepository.save(finalRelic);
        }

        //보스 유물 선택 저장
        JsonNode bossRelics = gameData.get("boss_relics");
        int act = 1;
        for (JsonNode bossRelicNode : bossRelics) {
            String pickedRelicName  = bossRelicNode.get("picked").asText();

            if (!"SKIP".equalsIgnoreCase(pickedRelicName)) {
                Relic pickedRelic = relicRepository.findByName(pickedRelicName).orElseThrow(() -> new CustomException(RELIC_NOT_FOUND));

                SelectBossRelic selectedRelic = new SelectBossRelic();
                selectedRelic.setGame(game);
                selectedRelic.setRelic(pickedRelic);
                selectedRelic.setAct(act);
                selectedRelic.setSelect(true);
                selectBossRelicRepository.save(selectedRelic);

                for (JsonNode notPickedNode : bossRelicNode.get("not_picked")) {
                    String notPickedRelicName = notPickedNode.asText();
                    Relic notPickedRelic = relicRepository.findByName(notPickedRelicName).orElseThrow(() -> new CustomException(RELIC_NOT_FOUND));

                    SelectBossRelic unselectedRelic = new SelectBossRelic();
                    unselectedRelic.setGame(game);
                    unselectedRelic.setRelic(notPickedRelic);
                    unselectedRelic.setSelect(false);
                    selectBossRelicRepository.save(unselectedRelic);
                }
            }
        }
    }

    @Transactional
    public void deleteGameData(String playId) {
        Game game = gameRepository.findByGameUUID(playId).orElseThrow(() -> new CustomException(GAME_NOT_FOUND));
        //cascade 형태로 게임 관련 테이블 전부 삭제
        battleRepository.deleteByGame(game);
        finalCardRepository.deleteByGame(game);
        finalRelicRepository.deleteByGame(game);
        selectBossRelicRepository.deleteByGame(game);
        selectedCardRewordRepository.deleteByGame(game);
        gameRepository.delete(game);

        String filePath = "src/main/resources/uploads/" + playId + ".json";
        File file = new File(filePath);

        if (file.exists()) {
            if (!file.delete()) {
                throw new CustomException(FILE_DELETE_FAILED);
            }
        } else {
            throw new CustomException(FILE_NOT_FOUND);
        }
    }

    public String findCauseOfDeathByFloor(JsonNode gameData, int targetFloor) {
        JsonNode damageTakenArray = gameData.get("damage_taken");
        //적으로 인해서 죽었을 경우 이것을 반환
        if (damageTakenArray != null && damageTakenArray.isArray()) {
            for (JsonNode damageNode : damageTakenArray) {
                if (damageNode.get("floor").asInt() == targetFloor) {
                    return damageNode.get("enemies").asText();
                }
            }
        }
        //이벤트로 인하여 죽었을 경우 이것을 반환
        JsonNode eventChoicesArray = gameData.get("event_choices");
        if (eventChoicesArray != null && eventChoicesArray.isArray()) {
            for (JsonNode eventNode : eventChoicesArray) {
                if (eventNode.get("floor").asInt() == targetFloor) {
                    return eventNode.get("event_name").asText();
                }
            }
        }
        return null;
    }

    public static LocalDateTime parseLocalTime(String localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return LocalDateTime.parse(localTime, formatter);
    }

    public static LocalTime convertPlaytimeToTime(int playtimeInSeconds) {
        return LocalTime.ofSecondOfDay(playtimeInSeconds);
    }

    public static CharacterType convertStringToCharacterType(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Null value cannot be converted to CharacterType");
        }

        // "THE_"가 문자열 앞에 있으면 제거
        String normalizedValue = value.toUpperCase();
        if (normalizedValue.startsWith("THE_")) {
            normalizedValue = normalizedValue.substring(4);
        }

        try {
            // 열거형으로 변환 시도
            return CharacterType.valueOf(normalizedValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid character type: " + value);
        }
    }

    private String removeNumberCardName(String cardName) {
        int plusIndex = cardName.indexOf('+');
        if (plusIndex != -1) {
            return cardName.substring(0, plusIndex + 1);
        }
        return cardName;
    }
}
