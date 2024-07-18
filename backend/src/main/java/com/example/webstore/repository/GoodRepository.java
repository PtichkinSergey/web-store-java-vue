package com.example.webstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.webstore.model.Good;
import com.example.webstore.model.Category;
import java.util.Set;


public interface GoodRepository extends CrudRepository<Good, Integer>{
    @Query(value = "Select a from Good a Order By Desc")
    List<Good> findAllDesc();
    @Query(value = "Select a from Good a Order By Asc")
    List<Good> findAllAsc();
    List<Good> findAllByCategoriesInOrderByCostDesc(Set<Category> categories);
    List<Good> findAllByCategoriesInOrderByCostAsc(Set<Category> categories);
}
