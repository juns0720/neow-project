package com.example.NeowProject.repository;

import com.example.NeowProject.domain.FinalRelic;
import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Relic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalRelicRepository extends JpaRepository<FinalRelic, Long> {
    void deleteByGame(Game game);
  
    List<FinalRelic> findByGame(Game game);

    //통계 관련 메소드
    long countByRelicAndGameVictoryTrue(Relic relic);
    long countByRelic(Relic relic);
}
