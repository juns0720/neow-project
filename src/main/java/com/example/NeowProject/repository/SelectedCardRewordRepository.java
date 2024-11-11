package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.SelectBattleRewardCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedCardRewordRepository extends JpaRepository<SelectBattleRewardCard, Long> {
    void deleteByGame(Game Game);

    //통계 관련 메소드들
    long countByCardAndSelectTrue(Card card);
    long countByCard(Card card);
    long countByCardAndFloorLessThanEqualAndSelectTrue(Card card, int maxFloor);
    long countByCardAndFloorLessThanEqual(Card card, int maxFloor);
    long countByCardAndFloorBetweenAndSelectTrue(Card card, int minFloor, int maxFloor);
    long countByCardAndFloorBetween(Card card, int minFloor, int maxFloor);
    long countByCardAndFloorGreaterThanAndSelectTrue(Card card, int minFloor);
    long countByCardAndFloorGreaterThan(Card card, int minFloor);
}
