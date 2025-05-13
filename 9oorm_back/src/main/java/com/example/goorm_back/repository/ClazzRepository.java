package com.example.goorm_back.repository;

import com.example.goorm_back.domain.clazz.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {

    Clazz findByClazzTitle(String clazzTitle);

}
