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
    @Test
    public void findBestRecordByMemberAndCharacterType() {

        Member member = new Member();
        member.setName("John");
        BestRecord bestRecord = setBestRecord(new BestRecord(), member, 10, 87.6, 19
                , 1250, 18273, CharacterType.IRONCLAD);

        memberService.join(member);
        memberService.saveBestRecord(bestRecord);

        assertEquals(bestRecord, bestRecordRepository.findOneByMemberAndCharacterType(member, CharacterType.IRONCLAD));
    }




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