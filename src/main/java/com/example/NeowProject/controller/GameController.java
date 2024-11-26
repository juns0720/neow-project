package com.example.NeowProject.controller;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.dto.response.BestRecordResponse;
import com.example.NeowProject.dto.response.PlayRecordsResponse;
import com.example.NeowProject.service.GameService;
import com.example.NeowProject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {

    private final GameService gameService;

    private final MemberService memberService;

    public GameController(GameService gameService, MemberService memberService) {
        this.gameService = gameService;
        this.memberService = memberService;
    }




    @GetMapping("api/users/{userId}/records")
    public ResponseEntity<?> list(@PathVariable("userId") Long userId, Model model) {
        Member member = memberService.findOneMember(userId);
        PlayRecordsResponse records = gameService.getPlayRecords(member);

        return ResponseEntity.ok(records);

    }

    @GetMapping("/api/users/{userId}/statistics/bestRecord/{characterType}")
    public ResponseEntity<?> bestRecord(@PathVariable("userId") Long userId, @PathVariable("characterType") CharacterType characterType) {
        Member member = memberService.findOneMember(userId);
        BestRecord bestRecord = gameService.findBestRecordByMemberAndCharacterType(member, characterType);

        BestRecordResponse bestRecordResponse = new BestRecordResponse(bestRecord);
        return ResponseEntity.ok(bestRecordResponse);
    }

    @GetMapping("/api/users/{userId}/statistics/bestRecords")
    public ResponseEntity<?> bestRecords(@PathVariable("userId") Long userId) {
        Member member = memberService.findOneMember(userId);
        List<BestRecord> bestRecords = gameService.findBestRecordsByMember(member);

        List<BestRecordResponse> bestRecordResponses = new ArrayList<>();
        for (BestRecord bestRecord : bestRecords) {
            bestRecordResponses.add(new BestRecordResponse(bestRecord));
        }
        return ResponseEntity.ok(bestRecordResponses);
    }

}
