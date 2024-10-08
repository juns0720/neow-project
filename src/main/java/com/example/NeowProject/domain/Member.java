package com.example.NeowProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter @Setter
public class Member {

    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private UUID user_id;

    private String password;

    private String name;
}
