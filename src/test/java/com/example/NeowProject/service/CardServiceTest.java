package com.example.NeowProject.service;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardType;
import com.example.NeowProject.domain.Color;
import com.example.NeowProject.domain.Rarity;
import com.example.NeowProject.repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CardServiceTest {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardService cardService;

    @Test
    void findById() {
        Card card = createCard("박치기", Color.RED, 2, CardType.ATTACK, Rarity.COMMON);

        Long savedId = cardService.save(card);

        assertEquals(savedId, cardService.findById(savedId).getId());
    }

    @Test
    void findAll() {
        Card card1 = createCard("박치기", Color.RED, 2, CardType.ATTACK, Rarity.COMMON);
        Card card2 = createCard("던지기", Color.RED, 2, CardType.ATTACK, Rarity.COMMON);

        cardService.save(card1);
        cardService.save(card2);

        assertEquals(cardService.findAll().size(), 2);

    }

    @Test
    void findCardsByCardType() {
        Card card1 = createCard("박치기", Color.RED, 2, CardType.ATTACK, Rarity.COMMON);
        Card card2 = createCard("던지기", Color.RED, 2, CardType.POWER, Rarity.COMMON);
        Card card3 = createCard("잡기", Color.RED, 2, CardType.POWER, Rarity.COMMON);

        cardService.save(card1);
        cardService.save(card2);
        cardService.save(card3);

        assertEquals(cardService.findCardByType(CardType.POWER).size(), 2);

    }

    @Test
    void findCardsByColor() {
        Card card1 = createCard("박치기", Color.RED, 2, CardType.ATTACK, Rarity.COMMON);
        Card card2 = createCard("던지기", Color.CURSE, 2, CardType.POWER, Rarity.COMMON);
        Card card3 = createCard("잡기", Color.RED, 2, CardType.POWER, Rarity.COMMON);

        cardService.save(card1);
        cardService.save(card2);
        cardService.save(card3);

        assertEquals(cardService.findCardByColor(Color.CURSE).size(), 1);
        assertEquals(cardService.findCardByColor(Color.RED).size(), 2);
    }


    static Card createCard(String name, Color color, int cost, CardType cardType, Rarity rarity) {
        Card card = new Card();
        card.setName(name);
        card.setColor(color);
        card.setCost(cost);
        card.setCardType(cardType);
        card.setRarity(rarity);
        return card;
    }
}