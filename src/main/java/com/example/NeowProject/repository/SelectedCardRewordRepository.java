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
    long countByCardAndPickTrue(Card card);
    long countByCard(Card card);
    long countByCardAndFloorLessThanEqualAndPickTrue(Card card, int maxFloor);
    long countByCardAndFloorLessThanEqual(Card card, int maxFloor);
    long countByCardAndFloorBetweenAndPickTrue(Card card, int minFloor, int maxFloor);
    long countByCardAndFloorBetween(Card card, int minFloor, int maxFloor);
    long countByCardAndFloorGreaterThanAndPickTrue(Card card, int minFloor);
    long countByCardAndFloorGreaterThan(Card card, int minFloor);
}
