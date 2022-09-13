package com.example.moira.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TestUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userSeq;

    private String userId;

    private String password;
}
