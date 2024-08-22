package com.example.webstore.service.good;

import java.util.List;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.UnknownCategoryException;
import com.example.webstore.model.Good;

public interface GoodService {
    public Good create(Good good);
    public List<Good> readByCategoryWithSort(Integer ctgId, String sort) throws UnknownCategoryException;
    public List<Good> readAllOrderByCostDesc();
    public List<Good> readAllOrderByCostAsc();

    public Good assignGoodToCategory(Integer goodId, Integer ctgId);
    public Good findById(int id) throws GoodNotFoundException;
    public Good update(Good good);
    public List<Good> updateAll(List<Good> goods);
    public void delete(int id);
}
