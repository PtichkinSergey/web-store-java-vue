package com.example.webstore.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.webstore.model.Category;
import com.example.webstore.model.Good;

public interface GoodService {
    public Good create(Good good);
    public List<Good> readAll();
    public List<Good> readAllOrderByCostDesc();
    public List<Good> readAllOrderByCostAsc();
    public List<Good> readByCategoryOrderByCostDesc(Set<Category> categories);
    public List<Good> readByCategoryOrderByCostAsc(Set<Category> categories);
    public Good assignGoodToCategory(Integer goodId, Integer ctgId);
    public Optional<Good> findById(int id);
    public Good update(Good good);
    public void delete(int id);
}
