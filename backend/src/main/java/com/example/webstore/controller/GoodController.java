package com.example.webstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.model.Good;
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
 */
@RestController
@RequestMapping("/api")
public class GoodController {
    private final GoodServiceImpl goodService;

    public GoodController(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    // Получение списка товаров по выбранной категории
    @GetMapping("/goods")
    public ResponseEntity<List<Good>> getAllGoods(@RequestParam("category") Integer ctgId, @RequestParam("sort") String sort) {
        try {
            List<Good> goodList = goodService.readByCategoryWithSort(ctgId, sort);
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
        try {
            Good goodData = goodService.findById(id); 
            return new ResponseEntity<>(goodData, HttpStatus.OK);
        } catch (GoodNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Обработчик запроса на привязку товара к категории. Отрабатывает при инициализации.
    @PutMapping("/{goodId}/category/{ctgId}")
    public Good assignGoodToCategory(@PathVariable Integer goodId, @PathVariable Integer ctgId) {
        return goodService.assignGoodToCategory(goodId, ctgId);
    }
}
