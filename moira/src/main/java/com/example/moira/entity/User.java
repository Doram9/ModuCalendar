package com.example.moira.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer seq;

    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String name;

    @Column
    private int age;

    @CreatedDate
    private LocalDateTime regDt;

    @Column(columnDefinition = "int(10) default '0'")
    private int role;

    @Builder
    public User(String id, String pw, String name, int age) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.age = age;
    }

}
