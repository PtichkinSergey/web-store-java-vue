package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.Category;

public interface CategoryService {
    public Category create(Category category);
    public List<Category> readAll();
    public Optional<Category> findById(int id);
    public Category update(Category category);
    public void delete(int id);
}
