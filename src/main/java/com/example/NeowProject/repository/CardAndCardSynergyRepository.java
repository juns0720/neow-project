package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardAndCardSynergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardAndCardSynergyRepository extends JpaRepository<CardAndCardSynergy, Long> {
    Optional<CardAndCardSynergy> findByCard1AndCard2(Card card1, Card card2);
}
