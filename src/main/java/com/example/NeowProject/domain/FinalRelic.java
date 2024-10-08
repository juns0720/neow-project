package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter@Setter
public class FinalRelic {
    @Id@GeneratedValue
    @Column(name = "final_relic_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "relic_id")
    private Relic relic;


    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id")
    private Game game;
}
