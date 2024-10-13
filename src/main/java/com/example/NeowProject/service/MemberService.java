package com.example.NeowProject.service;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import com.example.NeowProject.dto.BestRecordUpdateDto;
import com.example.NeowProject.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Member 기능
    @Transactional
    public Long join(Member member) {
        validateDuplicatemember(member);
        memberRepository.saveMember(member);
        return member.getId();
    }

    private void validateDuplicatemember(Member member) {
        List<Member> findMembers = memberRepository.findMemberByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findOneMember(Long memberId) {
        return memberRepository.findOneMember(memberId);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAllMembers();
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
        memberRepository.saveBestRecord(bestRecord);
        return bestRecord.getId();
    }

    public BestRecord findOneBestRecords(Member member, CharacterType characterType) {
        return memberRepository.findOneBestRecord(member, characterType);
    }

    public List<BestRecord> findAllBestRecords(Member member) {
        return memberRepository.findAllBestRecord(member);
    }

    @Transactional
    public void updateBestRecord(Member member, CharacterType characterType, BestRecordUpdateDto dto) {
        BestRecord bestRecord = memberRepository.findOneBestRecord(member, characterType);

        if (dto.getMaxAscension() != null) {
            bestRecord.setMaxAscension(dto.getMaxAscension());
        }
        if (dto.getWinRate() != null) {
            bestRecord.setWinRate(dto.getWinRate());
        }
        if (dto.getMaxStreak() != null) {
            bestRecord.setMaxStreak(dto.getMaxStreak());
        }
        if (dto.getMinTime() != null) {
            bestRecord.setMinTime(dto.getMinTime());
        }
        if (dto.getBestScore() != null) {
            bestRecord.setBestScore(dto.getBestScore());
        }
    }




}
