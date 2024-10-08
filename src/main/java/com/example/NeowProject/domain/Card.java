package com.example.NeowProject.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Card {

    @Id @GeneratedValue
    @Column(name = "card_id")
    private Long id;

    private String name;

    private Color color;

    private int cost;

    private CardType cardType;

    private Rarity rarity;
}
