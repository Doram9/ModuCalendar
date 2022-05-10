package kopo.poly.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Builder //sql 사용시 파라미터에 값을 쉽게 넣어주기 위한 어노테이션
@ToString //객체값 확인을 위한 어노테이션
@AllArgsConstructor // ↘
@NoArgsConstructor // → 생성자를 자동 완성시켜주는 어노테이션
@Entity(name = "User")
public class UserEntity {


    //id아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //반드시 들어가야하는 어노테이션(pk에 해당한다)
    @Column(name = "ID", nullable = false, unique = true) //pk 조건(중복 허용x, 널 허용x)
    private String id;

    //email이메일
    @Column(name="EMAIL", nullable = false)
    private String email;

    //name이름
    @Column(name="NAME")
    private String name;

    //password비밀번호
    @Column(name="PW")
    private String password;

    //regdt생성일자
    @Column(name="REGDT")
    private String regDt;

    //mkAppoCnt약속생성횟수
    @Column(name="MKAPPOCNT")
    private String mkAppoCnt;

    //mkPrjCnt프로젝트생성횟수
    @Column(name="MKPRJCNT")
    private String mkPrjCnt;

}
