package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class SelectBossRelic {
    @Id @GeneratedValue
    @Column(name = "select_boss_relic_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "game_id")
    private Game game;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="relic_id")
    private Relic relic;

    private int act;

    private boolean isSelect;
}
