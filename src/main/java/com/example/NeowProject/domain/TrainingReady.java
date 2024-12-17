package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class TrainingReady {
    @Id
    @GeneratedValue
    @Column(name = "Training_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="game_id")
    private Game game;
}
