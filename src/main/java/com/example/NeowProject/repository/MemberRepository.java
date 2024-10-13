package com.example.NeowProject.repository;


import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    // Member 계층
    public void saveMember(Member member){
        em.persist(member);
    }

    public Member findOneMember(Long id){
        return em.find(Member.class, id);
    }


    public List<Member> findAllMembers(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findMemberByName(String name){
        return em.createQuery(" select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
    }



    // BestRecord 계층
    public void saveBestRecord(BestRecord bestRecord){
        em.persist(bestRecord);
    }

    public BestRecord findOneBestRecord(Member member, CharacterType characterType){
        return em.createQuery("select b from BestRecord b " +
                        "where b.member = :member and b.characterType = :character", BestRecord.class)
                .setParameter("member", member)
                .setParameter("character", characterType)
                .getSingleResult();
    }

    public List<BestRecord> findAllBestRecord(Member member){
        return em.createQuery("select b from BestRecord b where b.member = :member", BestRecord.class)
                .setParameter("member", member)
                .getResultList();
    }





}
