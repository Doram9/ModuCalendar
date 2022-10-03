package com.example.moira.repository;

import com.example.moira.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppoRepository extends JpaRepository<Appointment, String> {

    @Override
    List<Appointment> findAllById(Iterable<String> strings);
}
