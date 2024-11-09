package com.example.NeowProject;

import com.example.NeowProject.domain.*;
import com.example.NeowProject.repository.CardRepository;
import com.example.NeowProject.repository.EnemyRepository;
import com.example.NeowProject.repository.MemberRepository;
import com.example.NeowProject.repository.RelicRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;

@Component
public class CsvDataLoader implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private RelicRepository relicRepository;
    @Autowired
    private EnemyRepository enemyRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCsvCardData();
        loadCsvRelicData();
        loadCsvEnemyData();
        loadMember();
    }

    private void loadMember() {
        Member member = new Member();
        member.setName("Um");
        memberRepository.save(member);
    }
    private void loadCsvCardData() {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource("data/card_data.csv").getFile()))) {
            String[] line;
            reader.readNext(); // Header 스킵
            while ((line = reader.readNext()) != null) {
                Card card = new Card();
                card.setName(line[0]);
                card.setColor(Color.valueOf(line[1]));
                card.setCost(Integer.parseInt(line[2]));
                card.setCardType(CardType.valueOf(line[3]));
                card.setRarity(Rarity.valueOf(line[4]));

                cardRepository.save(card);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private void loadCsvRelicData() {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource("data/relic_data.csv").getFile()))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                Relic relic = new Relic();
                relic.setName(line[0]);
                relic.setRelicType(RelicType.valueOf(line[1]));

                relicRepository.save(relic);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private void loadCsvEnemyData() {
        try (CSVReader reader = new CSVReader(new FileReader(new ClassPathResource("data/enemy_data.csv").getFile()))) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                Enemy enemy = new Enemy();
                enemy.setName(line[1]);

                enemyRepository.save(enemy);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}

