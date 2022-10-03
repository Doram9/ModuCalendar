package com.example.moira.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User extends TimeEntity{

    @Id
    private String id;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int role;

    @Builder
    public User(String id, String pw, String name, int role) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.role = role;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAppoEntity> userappo = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserPrjEntity> userprj = new ArrayList<>();

}
