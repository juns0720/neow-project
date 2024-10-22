package com.example.NeowProject.repository;


import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByName(String name);













//
//
//    private final EntityManager em;
//
//    // Member 계층
//    public void saveMember(Member member){
//        em.persist(member);
//    }
//

//
//
//
//    public List<Member> findMemberByName(String name){
//        return em.createQuery(" select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
//    }
//
//
//






}
