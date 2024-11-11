package com.example.NeowProject.controller;


import com.example.NeowProject.domain.Member;
import com.example.NeowProject.service.FileService;
import com.example.NeowProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private MemberService memberService;


    @PostMapping("api/runfile/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        Member member = memberService.findOneMember(1L);

        String playId = fileService.saveJsonFile(file);

        fileService.saveGameData(playId, member);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/runfile/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("play_id") String playId) {

        fileService.deleteGameData(playId);

        return ResponseEntity.ok().build();
    }

}
