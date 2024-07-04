package com.example.webstore.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webstore.model.Category;
import com.example.webstore.model.Good;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.repository.GoodRepository;

@ExtendWith(MockitoExtension.class)
class GoodServiceImplTest {
    @InjectMocks
    private GoodServiceImpl goodService;
    @Mock
    private GoodRepository goodRepository;
    @Mock
    private CategoryRepository categoryRepository;
    
    @Test
    void assignGoodToCategory() {
        Good good1 = new Good("Iphone", 150000, 0, 11, "Apple", "description", "PathToImg");
        Category category1 = new Category("Smartphone");
        Mockito.when(goodRepository.findById(1)).thenReturn(Optional.of(good1));
        Mockito.when(categoryRepository.findById(1)).thenReturn(Optional.of(category1));
        Mockito.when(goodRepository.save(Mockito.any(Good.class))).thenAnswer(i -> i.getArgument(0));
        Assertions.assertEquals(category1, goodService.assignGoodToCategory(1, 1).getCategories().iterator().next());
    }
}
