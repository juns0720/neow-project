package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter@Setter
public class Game {
    @Id @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String gameUUID;

    private boolean isVictory;

    private int finalFloor;

    private LocalDateTime localTime;

    private String defeatedBy;

    private Time playTime;

    private int ascension;

    private int score;

    @Enumerated(EnumType.STRING)
    private CharacterType characterType;


}
