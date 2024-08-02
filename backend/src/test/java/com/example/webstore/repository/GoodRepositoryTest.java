package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.webstore.model.Good;

@SpringBootTest
@ActiveProfiles("test")
class GoodRepositoryTest {
    @Autowired
    private GoodRepository goodRepository;

    @AfterEach
    void clearDB() {
        goodRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Good good1 = goodRepository.save(new Good("Iphone", 150000, 0, 11, "Apple", "description", "PathToImg"));
        assertTrue(good1.getId() > 0);
        goodRepository.deleteById(good1.getId());
    }

    @Test
    void findByIdTest() {
        Good good1 = goodRepository.save(new Good("Iphone", 150000, 0, 11, "Apple", "description", "PathToImg"));
        assertEquals(good1.getId(), goodRepository.findById(good1.getId()).get().getId());
        goodRepository.deleteById(good1.getId());
    }

    @Test
    void findAllDescTest() {
        goodRepository.save(new Good("test1", 100, 0, 11, "Apple", "description", "PathToImg"));
        goodRepository.save(new Good("test2", 10000, 0, 11, "Apple", "description", "PathToImg"));
        goodRepository.save(new Good("test3", 1000, 0, 11, "Apple", "description", "PathToImg"));
        List<Good> goodList = new ArrayList<>();
        goodRepository.findAllDesc().forEach(goodList::add);
        assertEquals(3, goodList.size());
        int prevCost = goodList.get(0).getCost();
        for(Good good : goodList) {
            assertTrue(good.getCost() <= prevCost);
            prevCost = good.getCost();
        }
    }

    @Test
    void findAllAscTest() {
        goodRepository.save(new Good("test", 100, 0, 11, "Apple", "description", "PathToImg"));
        goodRepository.save(new Good("test", 10000, 0, 11, "Apple", "description", "PathToImg"));
        goodRepository.save(new Good("test", 1000, 0, 11, "Apple", "description", "PathToImg"));
        List<Good> goodList = new ArrayList<>();
        goodRepository.findAllAsc().forEach(goodList::add);
        assertEquals(3, goodList.size());
        int prevCost = goodList.get(0).getCost();
        for(Good good : goodList) {
            assertTrue(good.getCost() >= prevCost);
            prevCost = good.getCost();
        }
    }

    @Test
    void deleteByIdTest() {
        Good good1 = goodRepository.save(new Good("Iphone", 150000, 0, 11, "Apple", "description", "PathToImg"));
        goodRepository.deleteById(good1.getId());
        assertFalse(goodRepository.findById(good1.getId()).isPresent());
    }
}
