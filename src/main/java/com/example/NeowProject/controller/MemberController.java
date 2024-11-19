package com.example.NeowProject.controller;

import com.example.NeowProject.domain.Member;
import com.example.NeowProject.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/api/users/register")
    public ResponseEntity<Map<String, String>> join(@RequestBody Member member) {
        Map<String, String> response = new HashMap<>();

        try {
            Long memberId = memberService.join(member);

            // 성공
            response.put("success", "회원 가입 성공, ID: " + memberId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalStateException e) {

            // 실패
            response.put("fail", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/users/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginRequest) {
        String userId = loginRequest.get("id");
        String password = loginRequest.get("password");

        Optional<Member> memberOptional = memberService.login(userId, password);

        if (memberOptional.isPresent()) {
            return ResponseEntity.ok().body(Map.of("success", "로그인 성공"));
        } else {
            return ResponseEntity.status(401).body(Map.of("fail", "로그인 실패, 아이디나 비밀번호가 잘못되었습니다."));
        }
    }
}

