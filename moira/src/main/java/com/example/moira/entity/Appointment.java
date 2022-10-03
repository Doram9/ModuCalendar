package com.example.moira.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "appoinfo")
public class Appointment extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String managerId;

    @Column(nullable = false)
    private String meetMonth;

    @Column(nullable = false)
    private int voteLimit;

    @Column(nullable = true)
    private String bestDate;

    @OneToMany
    @JoinColumn(name = "appoinfo_code")
    private List<UserAppoEntity> userAppoEntities = new ArrayList<>();

}
