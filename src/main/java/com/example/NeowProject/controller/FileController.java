package com.example.NeowProject.controller;


import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.service.FileService;
import com.example.NeowProject.service.GameService;
import com.example.NeowProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FileController {
    private final FileService fileService;
    private final MemberService memberService;
    private final GameService gameService;


    public FileController(FileService fileService, MemberService memberService, GameService gameService) {
        this.fileService = fileService;
        this.memberService = memberService;
        this.gameService = gameService;

    }


    @PostMapping("/upload/{userId}")
    public ResponseEntity<?> uploadFiles(@PathVariable("userId") Long userId, @RequestParam("files") List<MultipartFile> files) {
        try {
            // 다중 파일 처리 서비스 호출
            List<Game> processedGames = fileService.processMultipleFiles(files, userId);

            // 성공적으로 처리된 파일 수 응답
            return ResponseEntity.ok("Processed " + processedGames.size() + " files successfully.");
        } catch (Exception e) {
            // 처리 중 에러 발생 시 예외 메시지 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing files: " + e.getMessage());
        }
    }

    @DeleteMapping("api/runfile/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("play_id") String playId) {

        fileService.deleteGameData(playId);

        return ResponseEntity.ok().build();
    }

}
