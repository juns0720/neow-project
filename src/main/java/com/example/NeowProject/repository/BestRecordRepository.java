package com.example.NeowProject.repository;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BestRecordRepository extends JpaRepository<BestRecord, Long> {

    BestRecord findOneByMemberAndCharacterType(Member member, CharacterType characterType);

    List<BestRecord> findAllByMember(Member member);


}