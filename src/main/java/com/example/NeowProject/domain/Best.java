package com.example.NeowProject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Best {

    @Id@GeneratedValue
    @Column(name = "best_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private int maxAscensionLevel;

    private Double winRate;

    private int mostWinningStreak;

    private int minTime;

    private int bestScore;

    private Character character;
}
