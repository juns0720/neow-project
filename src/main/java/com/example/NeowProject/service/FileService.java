package com.example.NeowProject.service;

import com.example.NeowProject.exception.CustomException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.NeowProject.exception.ErrorCode.FILE_SAVE_FAILED;

@Service
public class FileService {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveJsonFile(MultipartFile file) {
        try {
            JsonNode jsonNode = objectMapper.readTree(file.getInputStream());

            String playId = jsonNode.get("play_id").asText();

            Path resourceDirectory = Paths.get("src", "main", "resources", "uploads");
            if (!Files.exists(resourceDirectory)) {
                Files.createDirectories(resourceDirectory); // 폴더가 없을 경우 생성
            }
            Path filePath = resourceDirectory.resolve(playId + ".json");

            Files.write(filePath, file.getBytes());
        } catch (IOException e) {
            throw new CustomException(FILE_SAVE_FAILED);
        }
    }
}
