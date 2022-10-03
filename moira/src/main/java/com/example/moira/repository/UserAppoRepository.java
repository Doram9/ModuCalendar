package com.example.moira.repository;

import com.example.moira.entity.UserAppoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAppoRepository extends JpaRepository<UserAppoEntity, Integer> {

    List<UserAppoEntity> findAllByUserId(String user_id);
}
