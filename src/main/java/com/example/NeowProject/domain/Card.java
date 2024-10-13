package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Card {

    @Id @GeneratedValue
    @Column(name = "card_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Color color;

    private Integer cost;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;
}
