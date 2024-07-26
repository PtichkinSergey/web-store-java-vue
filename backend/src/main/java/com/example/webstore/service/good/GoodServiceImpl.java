package com.example.webstore.service.good;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return goodRepository.findAllDesc();
    }

    /**
     * @return Выборка всего списка товаров в порядке возрастания цены
     */
    @Override
    public List<Good> readAllOrderByCostAsc() {
        return goodRepository.findAllAsc();
    }

    /**
     * @return Выборка товаров по заданным категориям в порядке возрастания цены
     */
    @Override
    public List<Good> readByCategoryOrderByCostAsc(Set<Category> categories) {
        return goodRepository.findByCategoriesAsc(categories);
    }

    /**
     * @return Выборка товаров по заданным категориям в порядке убывания цены
     */
    @Override
    public List<Good> readByCategoryOrderByCostDesc(Set<Category> categories) {
        return goodRepository.findByCategoriesDesc(categories);
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
    public Optional<Good> findById(int id) {
        return goodRepository.findById(id);
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
    
}
