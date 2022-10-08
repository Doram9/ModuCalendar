package com.example.moira.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "teammember")
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userName;

    //팀원 역할(아무거나 기재가능)
    @Column(nullable = true)
    private String userRole;

    //팀원 권한(master=프로젝트삭제가능, senior=마일스톤수정가능팀원, junior=마일스톤수정불가능팀원)
    @Column(nullable = false)
    private String userGrant;

    @ManyToOne
    @JoinColumn(name = "teamprj_code")
    private TeamPrj teamprj;
}
