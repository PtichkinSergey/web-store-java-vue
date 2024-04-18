package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Good;
import com.example.webstore.repository.GoodRepository;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private final GoodRepository goodRepository;

    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Override
    public Good create(Good good) {
        return goodRepository.save(good);
    }

    @Override
    public List<Good> readAll() {
        return goodRepository.findAll();
    }

    @Override
    public Optional<Good> findById(int id) {
        return goodRepository.findById(id);
    }

    @Override
    public Good update(Good good) {
        return goodRepository.save(good);
    }

    @Override
    public void delete(int id) {
        goodRepository.deleteById(id);
    }
    
}
