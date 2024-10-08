package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter@Setter
public class CardAndRelicSynergy {
    @Id@GeneratedValue
    @Column(name = "card_and_relic_synergy_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "relic_id")
    private Relic relic;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card;

    private Double synergy;
}
