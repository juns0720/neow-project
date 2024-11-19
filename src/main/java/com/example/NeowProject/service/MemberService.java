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

    public MemberService(MemberRepository memberRepository , BestRecordRepository bestRecordRepository) {
        this.memberRepository = memberRepository;
        this.bestRecordRepository = bestRecordRepository;

    }

    // Member 기능
    @Transactional
    public Long join(Member member) {
        validateDuplicatemember(member);
        memberRepository.save(member);
        initBestRecord(member);
        return member.getId();
    }

    public Optional<Member> login(String id, String password) {
        Optional<Member> memberOptional = memberRepository.findByUserId(id);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            if (member.getPassword().equals(password)) {
                return Optional.of(member);
            }
        }
        return Optional.empty();
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
