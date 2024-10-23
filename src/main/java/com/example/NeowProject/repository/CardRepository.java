package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardType;
import com.example.NeowProject.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findCardsByName(String name);

    List<Card> findCardsByCardType(CardType cardType);

    List<Card> findCardsByColor(Color color);

}
