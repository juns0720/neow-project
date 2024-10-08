package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class CardAndCardSynergy {

    @Id@GeneratedValue
    @Column(name = "card_and_card_synergy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "card1_id")
    private Card card1;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "card2_id")
    private Card card2;

    private Double synergy;
}
