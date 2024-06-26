package com.example.webstore.service;

import java.util.ArrayList;
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
    public List<Good> selectByCategory(int category_id) {
        List<Good> allGoods = goodRepository.findAllByOrderByCostDesc();
        List<Good> filteredList = new ArrayList<Good>();
        for(int i = 0; i < allGoods.size(); i++) {
            Set<Category> categories = allGoods.get(i).getCategories();
            for(int j = 0; j < categories.size(); j++) {
                if(((Category)(categories.toArray()[j])).getId() == category_id) {
                    filteredList.add(allGoods.get(i));
                    break;
                }
            }
        }
        return filteredList;
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
