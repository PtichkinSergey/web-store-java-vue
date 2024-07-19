package com.example.webstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.webstore.model.Good;
import com.example.webstore.model.Category;
import java.util.Set;


public interface GoodRepository extends CrudRepository<Good, Integer>{
    @Query(value = "Select a from Good a Order By a.cost Desc")
    List<Good> findAllDesc();
    @Query(value = "Select a from Good a Order By a.cost Asc")
    List<Good> findAllAsc();
    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Desc")
    List<Good> findByCategoriesDesc(Set<Category> categories);
    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Asc")
    List<Good> findByCategoriesAsc(Set<Category> categories);
}
