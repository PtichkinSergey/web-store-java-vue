package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.webstore.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void saveCategory() {
        Category category = categoryRepository.save(new Category("testCategory"));
        assertTrue(category.getId() > 0);
    }

    @Test
    void findByIdTest() {
        Category category = categoryRepository.save(new Category("testCategory"));
        assertEquals(category, categoryRepository.findById(category.getId()).get());
    }

    // @Test
    // void findAllTest() {
    //     Category category1 = categoryRepository.save(new Category("testCategory1"));
    //     Category category2 = categoryRepository.save(new Category("testCategory2"));
    //     Category category3 = categoryRepository.save(new Category("testCategory3"));
    //     categoryRepository.save(category1);
    //     categoryRepository.save(category2);
    //     categoryRepository.save(category3);
    //     List<Category> categoryList = new ArrayList<>();
    //     categoryRepository.findAll().forEach(categoryList::add);
    //     assertEquals(3, categoryList.size());
    // }

    // @Test
    // void deleteByIdTest() {
    //     Category category1 = categoryRepository.save(new Category("testCategory1"));
    //     categoryRepository.save(category1);
    //     categoryRepository.deleteById(category1.getId());
    //     List<Category> categoryList = new ArrayList<>();
    //     categoryRepository.findAll().forEach(categoryList::add);
    //     assertEquals(0, categoryList.size());
    // }

}
