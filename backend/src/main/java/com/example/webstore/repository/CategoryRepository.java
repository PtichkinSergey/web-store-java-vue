package com.example.webstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.webstore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    
}
