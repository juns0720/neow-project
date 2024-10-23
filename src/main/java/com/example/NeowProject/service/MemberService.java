package com.example.NeowProject.service;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.repository.BestRecordRepository;
import com.example.NeowProject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final BestRecordRepository bestRecordRepository;

    public MemberService(MemberRepository memberRepository, BestRecordRepository bestRecordRepository) {
        this.memberRepository = memberRepository;
        this.bestRecordRepository = bestRecordRepository;
    }

    // Member 기능
    @Transactional
    public Long join(Member member) {
        validateDuplicatemember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatemember(Member member) {
        Member findMembers = memberRepository.findByName(member.getName()).orElseThrow(() -> new IllegalStateException("이미 존재하는 회원입니다."));

    }

    public Member findOneMember(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    /**
     * Member 정보 업데이트 기능 추가 필요
     */

    //BestRecord 기능
    /**
     * 1. 최고 기록 갱신
     * 2. 최고 기록 찾기
     */

    @Transactional
    public Long saveBestRecord(BestRecord bestRecord) {
        bestRecordRepository.save(bestRecord);
        return bestRecord.getId();
    }

    public BestRecord findBestRecordByMemberAndCharacterType(Member member, CharacterType characterType) {
        return bestRecordRepository.findOneByMemberAndCharacterType(member, characterType);
    }

    public List<BestRecord> findBestRecordsByMember(Member member) {
        return bestRecordRepository.findAllByMember(member);
    }


}
