package com.example.NeowProject.service;

import com.example.NeowProject.domain.Card;
import com.example.NeowProject.domain.CardType;
import com.example.NeowProject.domain.Color;
import com.example.NeowProject.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;


    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Long save(Card card){
        cardRepository.save(card);
        return card.getId();
    }

    public Card findById(Long id){
        return cardRepository.findById(id).orElse(null);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }

    public List<Card> findCardByType(CardType cardType){
        return cardRepository.findCardsByCardType(cardType);
    }

    public List<Card> findCardByColor(Color color){
        return cardRepository.findCardsByColor(color);
    }


}
