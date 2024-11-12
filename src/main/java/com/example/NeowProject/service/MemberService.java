package com.example.NeowProject.service;

import com.example.NeowProject.domain.Member;
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
        memberRepository.save(member);
        initBestRecord(member);
        return member.getId();
    }

    private void validateDuplicatemember(Member member) {
        if (memberRepository.findByName(member.getName()).isPresent())
                throw new IllegalStateException("이미 존재하는 회원입니다.");

    }

    public Member findOneMember(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }


    @Transactional
    public void initBestRecord(Member member) {

        for (CharacterType characterType : CharacterType.values()) {

            BestRecord bestRecord = new BestRecord();

            bestRecord.setMember(member);
            bestRecord.setMaxAscension(0);
            bestRecord.setMinTime(Integer.MAX_VALUE);
            bestRecord.setWin(0);
            bestRecord.setLose(0);
            bestRecord.setBestScore(0);
            bestRecord.setCharacterType(characterType);

            bestRecordRepository.save(bestRecord);

        }

    }

}
