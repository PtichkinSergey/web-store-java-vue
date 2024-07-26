package com.example.webstore.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.example.webstore.model.Category;
import com.example.webstore.model.Good;
import com.example.webstore.service.category.CategoryServiceImpl;
import com.example.webstore.service.good.GoodServiceImpl;

@ExtendWith(MockitoExtension.class)
class GoodControllerTest {
    @InjectMocks
    private GoodController goodController;
    @Mock
    private GoodServiceImpl goodService;
    @Mock 
    private CategoryServiceImpl categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void initSecurityContext() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(goodController).build();
    }

    @Test
    void getAllGoodsTest() throws Exception{
        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test");
        Good good2 = new Good("test2", 1000, 0, 10, "test", "test", "test");
        Good good3 = new Good("test3", 1000, 0, 4, "test", "test", "test");
        List<Good> goods = Arrays.asList(good1, good2, good3);
        Category category1 = new Category("category1");
        when(categoryService.findById(1)).thenReturn(Optional.of(category1));
        when(goodService.readByCategoryWithSort(1, "descending")).thenReturn(goods);
        mockMvc.perform(get("/api/goods").param("category", "1").param("sort", "descending"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
        verify(categoryService, times(1)).findById(Mockito.anyInt());
        verify(goodService, times(1)).readByCategoryWithSort(Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    void getGoodByIdTest() throws Exception{
        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test");
        when(goodService.findById(1)).thenReturn(Optional.of(good1));
        mockMvc.perform(get("/api/goods/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(good1.getName()));
        verify(goodService, times(1)).findById(Mockito.anyInt());
    }
}
