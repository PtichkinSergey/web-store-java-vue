package com.example.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    
}
