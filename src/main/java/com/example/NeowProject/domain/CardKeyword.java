package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter@Setter
public class CardKeyword {

    @Id@GeneratedValue
    @Column(name = "card_keyword_id")
    private Long id;

    @ManyToOne(fetch = LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "card_id")
    private Card card;

    private String keyword;
}
