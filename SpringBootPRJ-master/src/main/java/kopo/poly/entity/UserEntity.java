package kopo.poly.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder //sql 사용시 파라미터에 값을 쉽게 넣어주기 위한 어노테이션
@ToString //객체값 확인을 위한 어노테이션
@AllArgsConstructor // ↘
@NoArgsConstructor // → 생성자를 자동 완성시켜주는 어노테이션
@Entity(name = "User")
public class UserEntity {

    @Id //반드시 들어가야하는 어노테이션(pk에 해당한다)
    @Column(nullable = false, unique = true) //pk 조건(중복 허용x, 널 허용x)
    private String userId;


}
