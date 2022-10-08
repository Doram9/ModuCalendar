package com.example.moira.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "event")
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String start;

    @Column(nullable = true)
    private String end;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Event(String title, String start, String end) {
        this.title = title;
        this.start = start;
        this.end = end;
    }

}
