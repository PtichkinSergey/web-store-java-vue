package com.example.webstore.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.example.webstore.model.Category;
import com.example.webstore.service.category.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryServiceImpl categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void initSecurityContext() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategoriesTest() throws Exception {
        Category category1 = new Category("category1");
        Category category2 = new Category("category2");
        Category category3 = new Category("category3");
        List<Category> categories = Arrays.asList(category1, category2, category3);
        when(categoryService.readAll()).thenReturn(categories);
        
        mockMvc.perform(get("/api/categories"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
        verify(categoryService, times(1)).readAll();
    }

    @Test
    void getCategoryByIdTest() throws Exception {
        Category category1 = new Category("category1");
        when(categoryService.findById(Mockito.anyInt())).thenReturn(category1);
        mockMvc.perform(get("/api/categories/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(category1.getName()));
        verify(categoryService, times(1)).findById(Mockito.anyInt());
    }
}
