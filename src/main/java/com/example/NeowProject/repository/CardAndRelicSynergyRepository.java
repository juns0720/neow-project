package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardAndRelicSynergy;
import com.example.NeowProject.domain.Relic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardAndRelicSynergyRepository extends JpaRepository<CardAndRelicSynergy, Long> {
    Optional<CardAndRelicSynergy> findByCardAndRelic(Card card, Relic relic);
}
