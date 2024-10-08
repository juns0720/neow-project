package com.example.NeowProject.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Relic {

    @Id@GeneratedValue
    @Column(name = "relic_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RelicType relicType;

    private String name;
}
