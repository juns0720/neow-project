package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter@Setter
public class BestRecord {

    @Id@GeneratedValue
    @Column(name = "best_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private int maxAscension;

    private int win;

    private int lose;

    private int minTime;

    private int bestScore;

    @Enumerated(EnumType.STRING)
    private CharacterType characterType;
}
