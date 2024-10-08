package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter@Setter
public class BattleRecord {
    @Id@GeneratedValue
    @Column(name = "battle_record_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "enermy_id")
    private Enemy enemy;

    private int win;

    private int lose;
}
