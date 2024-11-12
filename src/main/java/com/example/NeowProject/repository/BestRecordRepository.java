package com.example.NeowProject.repository;

import com.example.NeowProject.domain.BestRecord;
import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BestRecordRepository extends JpaRepository<BestRecord, Long> {

    BestRecord findOneByMemberAndCharacterType(Member member, CharacterType characterType);

    List<BestRecord> findAllByMember(Member member);

    @Query("SELECT b FROM BestRecord b WHERE b.member = :member AND (b.characterType = :characterType OR b.characterType = com.example.NeowProject.domain.CharacterType.ALL)")
    List<BestRecord> findBestRecordsForUpdate(@Param("member") Member member, @Param("characterType") CharacterType characterType);


}