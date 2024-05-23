package com.example.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Good;

public interface GoodRepository extends JpaRepository<Good, Integer>{
    
}
