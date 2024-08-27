package com.example.webstore.service.category;

import java.util.List;

import com.example.webstore.model.Category;

public interface CategoryService {
    public Category create(Category category);
    public List<Category> readAll();
    public Category findById(int id);
    public Category update(Category category);
    public void delete(int id);
}
