package com.example.moira.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "voteinfo")
public class VoteInfo extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(nullable = true)
    private String posDate;

    @Column(nullable = true)
    private String negDate;

    @Column(nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "code")
    private Appointment appo;
}
