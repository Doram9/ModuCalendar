package com.example.moira.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "mileinfo")
public class MileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String progress;

    @ManyToOne
    @JoinColumn(name = "teamprj_code")
    private TeamPrj teamprj;
}
