package com.example.webstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        
    }
}
