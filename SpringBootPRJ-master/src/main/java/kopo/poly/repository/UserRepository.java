package kopo.poly.repository;

import kopo.poly.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //Repository의 경우에는 JpaRepository를 extends 받으면 해당 Repository로 JPA의 대부분의 자동 쿼리 기능을 수행할 수 있다
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query(value = "select * from User where id = :id")
    List<UserEntity> selectUser(@Param("id")String id);
}
