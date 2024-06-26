package com.example.webstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Good;

public interface GoodRepository extends JpaRepository<Good, Integer>{
    List<Good> findAllByOrderByCostDesc();
    List<Good> findAllByOrderByCostAsc();
    // List<Good> findByActiveOrderByCostAsc(boolean active);
}
