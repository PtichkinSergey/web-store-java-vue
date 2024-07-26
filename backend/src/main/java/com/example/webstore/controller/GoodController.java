package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Category;
import com.example.webstore.model.Good;
import com.example.webstore.service.category.CategoryServiceImpl;
import com.example.webstore.service.good.GoodServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Контроллер запросов для доступа к товарам.
 * Внедряемые зависимости: 
 * goodService - сервис товаров
 * categoryService - сервис категорий
 */
@RestController
@RequestMapping("/api")
public class GoodController {
    private final GoodServiceImpl goodService;
    private final CategoryServiceImpl categoryService;

    public GoodController(GoodServiceImpl goodService, CategoryServiceImpl categoryService) {
        this.goodService = goodService;
        this.categoryService = categoryService;
    }

    // Получение списка товаров по выбранной категории
    @GetMapping("/goods")
    public ResponseEntity<List<Good>> getAllGoods(@RequestParam("category") Integer ctgId, @RequestParam("sort") String sort) {
        try {
            List<Good> goodList = new ArrayList<>();
            if(ctgId > 0) {
                Set<Category> categories = new HashSet<>();
                Optional<Category> ctg = categoryService.findById(ctgId);
                if(ctg.isPresent()) {
                    categories.add(ctg.get());
                }
                else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if(sort.equals("descending")) {
                    goodService.readByCategoryOrderByCostDesc(categories).forEach(goodList::add);
                }
                else {
                    goodService.readByCategoryOrderByCostAsc(categories).forEach(goodList::add);
                }
            }
            else {
                if(sort.equals("descending")) {
                    goodService.readAllOrderByCostDesc().forEach(goodList::add);
                }
                else {
                    goodService.readAllOrderByCostAsc().forEach(goodList::add);
                }
            }
            if (goodList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(goodList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Получение экземпляра конкретного товара
    @GetMapping("/goods/{id}")
    public ResponseEntity<Good> getGoodById(@PathVariable("id") int id) {
        Optional<Good> goodData = goodService.findById(id);
        if (goodData.isPresent()) {
            return new ResponseEntity<>(goodData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Обработчик запроса на привязку товара к категории. Отрабатывает при инициализации.
    @PutMapping("/{goodId}/category/{ctgId}")
    public Good assignGoodToCategory(@PathVariable Integer goodId, @PathVariable Integer ctgId) {
        return goodService.assignGoodToCategory(goodId, ctgId);
    }
}
