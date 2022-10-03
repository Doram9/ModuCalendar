package com.example.moira.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "userAppo")
public class UserAppoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;

    @ManyToOne
    private User user;

    @ManyToOne
    private Appointment appo;
}
