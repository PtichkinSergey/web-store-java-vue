package com.example.webstore.service.good;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.UnknownCategoryException;
import com.example.webstore.model.Category;
import com.example.webstore.model.Good;
import com.example.webstore.repository.CategoryRepository;
import com.example.webstore.repository.GoodRepository;

/**
 * Сервис для работы с товарами.
 * Внедряемые зависимости: 
 * goodRepository - crud репозиторий товаров
 * categoryRepository - crud репозиторий категорий
 */
@Service
public class GoodServiceImpl implements GoodService {
    private final GoodRepository goodRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public GoodServiceImpl(GoodRepository goodRepository, CategoryRepository categoryRepository) {
        this.goodRepository = goodRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Good create(Good good) {
        return goodRepository.save(good);
    }

    /**
     * @return Выборка всего списка товаров в порядке убывания цены
     */
    @Override
    public List<Good> readAllOrderByCostDesc() {
        List<Good> goodList = new ArrayList<>();
        goodRepository.findAllDesc().forEach(goodList::add);
        return goodList;
    }

    /**
     * @return Выборка всего списка товаров в порядке возрастания цены
     */
    @Override
    public List<Good> readAllOrderByCostAsc() {
        List<Good> goodList = new ArrayList<>();
        goodRepository.findAllAsc().forEach(goodList::add);
        return goodList;
    }

    /**
     * Связывание товара с категорией
     * @return Товар с добавленной категорией
     */
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
    public Good findById(int id) throws GoodNotFoundException{
        Optional<Good> good = goodRepository.findById(id);
        if (good.isPresent()) {
            return good.get();
        } else {
            throw new GoodNotFoundException(String.format("Товар с id: %s не найден!", id));
        }
    }

    @Override
    public Good update(Good good) {
        return goodRepository.save(good);
    }

    @Override
    public List<Good> updateAll(List<Good> goods) {
        return (List<Good>)goodRepository.saveAll(goods);
    }

    @Override
    public void delete(int id) {
        goodRepository.deleteById(id);
    }

    /**
     * @param 
     * @return Выборка товаров по заданным категориям в указанном порядке сортировки
     */
    @Override
    public List<Good> readByCategoryWithSort(Integer ctgId, String sort) throws UnknownCategoryException{
        List<Good> goodList = new ArrayList<>();
        if(ctgId > 0) {
            Set<Category> categories = new HashSet<>();
            Optional<Category> ctg = categoryRepository.findById(ctgId);
            if(ctg.isPresent()) {
                categories.add(ctg.get());
            } else {
                throw new UnknownCategoryException("Категория товаров отсутствует!");
            }
            if(sort.equals("descending")) {
                goodRepository.findByCategoriesDesc(categories).forEach(goodList::add);
            } else {
                goodRepository.findByCategoriesAsc(categories).forEach(goodList::add);
            }
        } else {
            if(sort.equals("descending")) {
                goodList = readAllOrderByCostDesc();
            } else {
                goodList = readAllOrderByCostAsc();
            }
        }
        return goodList;
    }
}
