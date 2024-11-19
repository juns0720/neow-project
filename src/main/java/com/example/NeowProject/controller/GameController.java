package com.example.NeowProject.controller;

import com.example.NeowProject.domain.Member;
import com.example.NeowProject.dto.response.PlayRecordsResponse;
import com.example.NeowProject.service.GameService;
import com.example.NeowProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {

    @Autowired
    GameService gameService;

    @Autowired
    MemberService memberService;


    @GetMapping("api/users/{userId}/records")
    public ResponseEntity<?> list(@PathVariable("userId") Long userId, Model model) {
        Member member = memberService.findOneMember(userId);
        PlayRecordsResponse records = gameService.getPlayRecords(member);

        return ResponseEntity.ok(records);

    }

}
