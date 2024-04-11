package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Good;
import com.example.webstore.service.GoodServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class GoodController {
    private final GoodServiceImpl goodService;

    public GoodController(GoodServiceImpl goodService) {
        this.goodService = goodService;
    }

    @GetMapping("/status")
    public ResponseEntity<List<Good>> getAllStatus() {
        try {
            List<Good> goodList = new ArrayList<Good>();
            goodService.readAll().forEach(goodList::add);
            if (goodList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(goodList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<Good> getStatusById(@PathVariable("id") int id) {
        Optional<Good> statusData = goodService.findById(id);
        if (statusData.isPresent()) {
            return new ResponseEntity<>(statusData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
