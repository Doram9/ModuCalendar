package com.example.moira.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "prjinfo")
public class TeamPrj extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String managerId;

    @Column(length = 20)
    private String startDate;

    @Column(length = 20)
    private String endDate;



}
