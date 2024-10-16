package com.example.NeowProject.repository;

import com.example.NeowProject.domain.CharacterType;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGameUUID(String gameUUID);

    List<Game> findGamesByMember(Member member);

    List<Game> findGamesByMemberAndCharacterType(Member member, CharacterType characterType);
}
