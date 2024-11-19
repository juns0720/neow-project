package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Game;
import com.example.NeowProject.domain.Relic;
import com.example.NeowProject.domain.SelectBossRelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectBossRelicRepository extends JpaRepository<SelectBossRelic, Long> {
    void deleteByGame(Game game);

    //통계 관련 메소드
    long countByRelicAndSelectTrue(Relic relic);
    long countByRelic(Relic relic);
}
