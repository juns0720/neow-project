package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.FinalCard;
import com.example.NeowProject.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalCardRepository extends JpaRepository<FinalCard, Long> {
    void deleteByGame(Game game);

    List<FinalCard> findByGame(Game game);

    //통계 관련 메소드
    long countByCardAndGameVictoryTrue(Card card);
    long countByCard(Card card);
}
