package com.example.NeowProject.service;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.repository.BestRecordRepository;
import com.example.NeowProject.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BestRecordRepository bestRecordRepository;


    /**
     * 멤버 정보 검색
     */
    @Test
//    @Rollback(false)
    public void findMemberById() {
        Member member = new Member();
        member.setName("John");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findById(savedId).get());
    }

    /**
     * 최고 기록 정보 검색
     */

}