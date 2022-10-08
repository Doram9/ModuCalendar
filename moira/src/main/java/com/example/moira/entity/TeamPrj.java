package com.example.moira.entity;


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
@Table(name = "teamprj")
public class TeamPrj extends TimeEntity{

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String managerId;

    @Column(length = 20)
    private String startDate;

    @Column(length = 20)
    private String endDate;

    @OneToMany(mappedBy = "teamprj", fetch = FetchType.LAZY)
    private List<UserPrjEntity> userprj = new ArrayList<>();


}
