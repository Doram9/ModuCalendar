package com.example.moira.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "userappo")
public class UserAppoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "appo_code")
    private Appointment appo;

    @Builder
    public UserAppoEntity(User user, Appointment appo) {
        this.user = user;
        this.appo = appo;
    }
}
