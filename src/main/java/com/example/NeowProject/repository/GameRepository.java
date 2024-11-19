package com.example.NeowProject.repository;

import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGameUUID(String gameUUID);

    List<Game> findGamesByMember(Member member);

    List<Game> findGamesByMemberAndCharacterType(Member member, CharacterType characterType);

    //통계 관련 메소드
    long countByCharacterTypeAndAscension(CharacterType characterType, int ascension);

    long countByCharacterTypeAndAscensionAndVictoryTrue(CharacterType characterType, int ascension);

    long countByCharacterType(CharacterType characterType);

    long countByVictoryFalse();

    long countByDefeatedByAndVictoryFalse(String defeatedBy);
}
