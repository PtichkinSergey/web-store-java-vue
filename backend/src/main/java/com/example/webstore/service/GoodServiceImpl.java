package com.example.webstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Category;
import com.example.webstore.model.Good;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.repository.GoodRepository;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private final GoodRepository goodRepository;

    @Autowired
    private final CategoryRepository categoryRepository;

    public GoodServiceImpl(GoodRepository goodRepository, CategoryRepository categoryRepository) {
        this.goodRepository = goodRepository;
        this.categoryRepository = categoryRepository;
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
    public List<Good> readAllOrderByCostDesc() {
        return goodRepository.findAllByOrderByCostDesc();
    }

    @Override
    public List<Good> readAllOrderByCostAsc() {
        return goodRepository.findAllByOrderByCostAsc();
    }

    @Override
    public List<Good> readByCategoryOrderByCostAsc(Set<Category> categories) {
        return goodRepository.findAllByCategoriesInOrderByCostAsc(categories);
    }

    @Override
    public List<Good> readByCategoryOrderByCostDesc(Set<Category> categories) {
        return goodRepository.findAllByCategoriesInOrderByCostDesc(categories);
    }

    @Override
    public Good assignGoodToCategory(Integer goodId, Integer ctgId) {
        Set<Category> categories = null;
        Good good = goodRepository.findById(goodId).get();
        Category category = categoryRepository.findById(ctgId).get();
        categories = good.getCategories();
        categories.add(category);
        good.setCategories(categories);
        return goodRepository.save(good);
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
