package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Battle {

    @Id @GeneratedValue
    @Column(name = "battle_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name= "enemy_id")
    private Enemy enemy;

    private int turn;

    private int damage;

    private int floor;



}
