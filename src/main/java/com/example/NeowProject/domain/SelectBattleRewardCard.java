package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter@Setter
public class SelectBattleRewardCard {

    @Id @GeneratedValue
    @Column(name="select_battle_reward_card_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="game_id")
    private Game game;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "card_id")
    private Card card;

    private int floor;

    private boolean pick;




}
