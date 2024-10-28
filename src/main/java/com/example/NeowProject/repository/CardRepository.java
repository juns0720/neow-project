package com.example.NeowProject.repository;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardType;
import com.example.NeowProject.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {


    List<Card> findCardsByCardType(CardType cardType);

    List<Card> findCardsByColor(Color color);

    Card findByName(String name);

}
