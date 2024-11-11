package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Battle;
import com.example.NeowProject.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {
    void deleteByGame(Game game);
}
