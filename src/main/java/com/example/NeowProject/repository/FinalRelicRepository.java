package com.example.NeowProject.repository;

import com.example.NeowProject.domain.FinalRelic;
import com.example.NeowProject.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalRelicRepository extends JpaRepository<FinalRelic, Long> {
    void deleteByGame(Game game);
  
    List<FinalRelic> findByGame(Game game);
}
