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




}
