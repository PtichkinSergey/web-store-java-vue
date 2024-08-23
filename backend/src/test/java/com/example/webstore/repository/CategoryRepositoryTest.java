package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.webstore.model.Category;

@SpringBootTest
@ActiveProfiles("test")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void clearDB() {
        categoryRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Category category = categoryRepository.save(new Category("testCategory"));
        assertTrue(category.getId() > 0);
        categoryRepository.deleteById(category.getId());
    }

    @Test
    void findByIdTest() {
        Category category = categoryRepository.save(new Category("testCategory"));
        assertEquals(category.getId(), categoryRepository.findById(category.getId()).get().getId());
        categoryRepository.deleteById(category.getId());
    }

    @Test
    void findAllTest() {
        categoryRepository.save(new Category("testCategory1"));
        categoryRepository.save(new Category("testCategory2"));
        categoryRepository.save(new Category("testCategory3"));
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findAll().forEach(categoryList::add);
        assertEquals(3, categoryList.size());
    }

    @Test
    void deleteByIdTest() {
        Category category1 = categoryRepository.save(new Category("testCategory1"));
        categoryRepository.deleteById(category1.getId());
        assertFalse(categoryRepository.findById(category1.getId()).isPresent());
    }
}
