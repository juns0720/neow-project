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


    @PostMapping("api/runfile/upload/{userId}")
    @CrossOrigin(origins =  "http://localhost:3000")
    public ResponseEntity<?> uploadFile(@PathVariable("userId")Long userId, @RequestParam("file") MultipartFile file) {
        Member member = memberService.findOneMember(userId);


        String playId = fileService.saveJsonFile(file);

        Game game = fileService.saveGameData(playId, member);
        gameService.updateBestRecord(member, game);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/runfile/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("play_id") String playId) {

        fileService.deleteGameData(playId);

        return ResponseEntity.ok().build();
    }

}
