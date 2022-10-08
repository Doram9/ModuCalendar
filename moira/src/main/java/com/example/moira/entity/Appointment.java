package com.example.moira.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "appo")
public class Appointment extends TimeEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String managerId;

    @Column(nullable = false)
    private String meetMonth;

    @Column(nullable = false)
    private int voteLimit;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String bestDate;

    @Builder
    private Appointment(String title, String managerId, String meetMonth, int voteLimit, String location, String bestDate) {
        this.title = title;
        this.managerId = managerId;
        this.meetMonth = meetMonth;
        this.voteLimit = voteLimit;
        this.location = location;
        this.bestDate = bestDate;
    }

    @OneToMany(mappedBy = "appo", fetch = FetchType.LAZY)
    private List<UserAppoEntity> userappo = new ArrayList<>();


}
