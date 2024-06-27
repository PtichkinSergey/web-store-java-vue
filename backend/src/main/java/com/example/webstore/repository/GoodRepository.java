package com.example.webstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Good;
import com.example.webstore.model.Category;
import java.util.Set;


public interface GoodRepository extends JpaRepository<Good, Integer>{
    List<Good> findAllByOrderByCostDesc();
    List<Good> findAllByOrderByCostAsc();
    List<Good> findAllByCategoriesInOrderByCostDesc(Set<Category> categories);
    List<Good> findAllByCategoriesInOrderByCostAsc(Set<Category> categories);
}
