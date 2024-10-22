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
     * 회원가입 로직
     */
    @Test
//    @Rollback(false)
    public void join() {
        Member member = new Member();
        member.setName("John");

        Long savedId = memberService.join(member);

        assertEquals(member, memberRepository.findById(savedId));
    }



    @Test
//    @Rollback(false)
    public void findOneBestRecords() {
        BestRecord bestRecord = new BestRecord();

        Member member = new Member();
        member.setName("John");

        memberService.join(member);

        bestRecord.setMember(member);

        bestRecord.setMaxAscension(10);
        bestRecord.setWinRate(87.6);
        bestRecord.setMaxStreak(19);
        bestRecord.setMinTime(1250);
        bestRecord.setBestScore(18273);
        bestRecord.setCharacterType(CharacterType.IRONCLAD);

        memberService.saveBestRecord(bestRecord);
//        assertEquals(bestRecord, memberRepository.findOneBestRecord(member, Character.IRONCLAD));

    }

//    @Test
//    @Rollback(false)
//    public void updateBestRecord() {
//
//
//        Member member = new Member();
//        member.setName("John");
//
//        memberService.join(member);
//        BestRecord bestRecord = setBestRecord(new BestRecord(), member, 10, 87.6, 19
//                , 1250, 18273, CharacterType.IRONCLAD);
//
//        BestRecord bestRecord1 = setBestRecord(new BestRecord(), member, 32, 27.5, 13
//                , 500, 2342, CharacterType.SILENT);
//
//        memberService.saveBestRecord(bestRecord);
//        memberService.saveBestRecord(bestRecord1);
//
//
//        BestRecordUpdateDto bestRecordUpdateDto = new BestRecordUpdateDto(13,null,25,null,null,CharacterType.IRONCLAD);
//
//        memberService.updateBestRecord(member, CharacterType.IRONCLAD, bestRecordUpdateDto);
//    }


    public static BestRecord setBestRecord(BestRecord bestRecord , Member member, int maxAscension, double winRate, int maxStreak,int minTime, int bestScore, CharacterType characterType) {
        bestRecord.setMember(member);
        bestRecord.setMaxAscension(maxAscension);
        bestRecord.setWinRate(winRate);
        bestRecord.setMinTime(minTime);
        bestRecord.setBestScore(bestScore);
        bestRecord.setCharacterType(characterType);

        return bestRecord;
    }
}