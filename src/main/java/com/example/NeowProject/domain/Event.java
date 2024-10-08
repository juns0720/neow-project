package com.example.NeowProject.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Event {
    @Id@GeneratedValue
    @Column(name = "event_id")
    private Long id;

    private String name;
}
