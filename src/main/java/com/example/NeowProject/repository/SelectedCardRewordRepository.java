package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.SelectBattleRewardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedCardRewordRepository extends JpaRepository<SelectBattleRewardCard, Long> {
    void deleteByGame(Game Game);

}
