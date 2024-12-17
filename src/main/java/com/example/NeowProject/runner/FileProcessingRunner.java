package com.example.NeowProject.runner;

import com.example.NeowProject.domain.*;
import com.example.NeowProject.repository.CardRepository;
import com.example.NeowProject.repository.EnemyRepository;
import com.example.NeowProject.repository.RelicRepository;
import com.example.NeowProject.service.FileService;
import com.example.NeowProject.service.MemberService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileProcessingRunner implements CommandLineRunner {

    private final FileService fileService;
    private final MemberService memberService;
    private final RelicRepository relicRepository;
    private final CardRepository cardRepository;
    private final EnemyRepository enemyRepository;

    // 로컬 경로 설정
    private static final String FILE_DIRECTORY = "src/main/resources/testdata";

    public FileProcessingRunner(FileService fileService, MemberService memberService, RelicRepository relicRepository, CardRepository cardRepository, EnemyRepository enemyRepository) {
        this.fileService = fileService;
        this.memberService = memberService;
        this.relicRepository = relicRepository;
        this.cardRepository = cardRepository;
        this.enemyRepository = enemyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCsvCardData();
        loadCsvRelicData();
        loadCsvEnemyData();
        loadMember();

        Long defaultUserId = 1L; // 기본 사용자 ID

        // 1. 로컬 경로에서 파일 읽기
        List<MultipartFile> files = getFilesFromLocalDirectory(FILE_DIRECTORY);
        if (files.isEmpty()) {
            System.out.println("No files found in directory: " + FILE_DIRECTORY);
        } else {
            // 2. 파일 처리 서비스 호출
            List<Game> processedGames = fileService.processMultipleFiles(files, defaultUserId);
            System.out.println("Processed " + processedGames.size() + " files successfully.");
        }

        System.out.println("=== File processing completed ===");
    }

    private List<MultipartFile> getFilesFromLocalDirectory(String directoryPath) {
        List<MultipartFile> multipartFiles = new ArrayList<>();

        try {
            Path directory = Paths.get(directoryPath);
            File folder = directory.toFile();

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles(); // 모든 파일 가져오기

                if (files != null) {
                    for (File file : files) {
                        try (FileInputStream inputStream = new FileInputStream(file)) {
                            MultipartFile multipartFile = new MockMultipartFile(
                                    file.getName(), // 원본 파일 이름
                                    file.getName(),
                                    "application/octet-stream", // MIME 타입
                                    inputStream
                            );
                            multipartFiles.add(multipartFile);
                        }
                    }
                }
            } else {
                System.err.println("Directory not found: " + directoryPath);
            }
        } catch (Exception e) {
            System.err.println("Error loading files from directory: " + e.getMessage());
            e.printStackTrace();
        }

        return multipartFiles;
    }

    private void loadMember() {
        Member member = new Member();
        member.setName("Um");
        memberService.join(member);
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
