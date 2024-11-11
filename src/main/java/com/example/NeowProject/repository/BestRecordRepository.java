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


    // 최고 ascension 갱신
    @Modifying
    @Transactional
    @Query("UPDATE BestRecord br SET br.maxAscension = :ascension " +
            "WHERE br.member = :member AND br.characterType = :characterType AND br.maxAscension < :ascension")
    void updateMaxAscension(@Param("member") Member member, @Param("characterType") CharacterType characterType, @Param("ascension") int ascension);

    // 승률(winRate) 갱신
    @Modifying
    @Transactional
    @Query("UPDATE BestRecord br SET br.winRate = ((br.winRate * :totalGames + :wins) / (:totalGames + 1)) " +
            "WHERE br.member = :member AND br.characterType = :characterType")
    void updateWinRate(@Param("member") Member member, @Param("characterType") CharacterType characterType,
                       @Param("totalGames") double totalGames, @Param("wins") double wins);

    // 최소 시간(minTime) 갱신
    @Modifying
    @Transactional
    @Query("UPDATE BestRecord br SET br.minTime = :time " +
            "WHERE br.member = :member AND br.characterType = :characterType AND br.minTime > :time")
    void updateMinTime(@Param("member") Member member, @Param("characterType") CharacterType characterType, @Param("time") int time);

    // 최고 점수(bestScore) 갱신
    @Modifying
    @Transactional
    @Query("UPDATE BestRecord br SET br.bestScore = :score " +
            "WHERE br.member = :member AND br.characterType = :characterType AND br.bestScore < :score")
    void updateBestScore(@Param("member") Member member, @Param("characterType") CharacterType characterType, @Param("score") int score);


}