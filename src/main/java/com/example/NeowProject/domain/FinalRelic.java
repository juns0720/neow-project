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
    private Relic relic;


    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private Game game;
}
