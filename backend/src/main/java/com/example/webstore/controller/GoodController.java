package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Good;
import com.example.webstore.service.GoodServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


// Контроллер сущности товара
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
            List<Good> goodList = new ArrayList<Good>();
            if(ctgId > 0) {
                goodService.selectByCategory(ctgId).forEach(goodList::add);
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
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

    // Создание нового товара в базе
    @PostMapping("/goods")
    public String postMethodName(@RequestBody Good good) {
        goodService.create(good);
        return "success";
    }
    
    @PutMapping("/{goodId}/category/{ctgId}")
    public Good assignGoodToCategory(@PathVariable Integer goodId, @PathVariable Integer ctgId) {
        return goodService.assignGoodToCategory(goodId, ctgId);
    }
}
