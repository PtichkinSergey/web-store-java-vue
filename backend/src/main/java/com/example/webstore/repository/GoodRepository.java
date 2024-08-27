package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.webstore.model.Good;
import com.example.webstore.model.Category;
import java.util.Set;

// Репозиторий для доступа к данным таблицы сущности товара
@Repository
public interface GoodRepository extends CrudRepository<Good, Integer>{
    @NonNull
    public Optional<Good> findById(@NonNull Integer id);

    @NonNull
    public Good save(@NonNull Good good);

    @NonNull
    public <T extends Good> Iterable<T> saveAll(@NonNull Iterable<T> entities);

    public void deleteById(@NonNull Integer id);

    @Query(value = "Select g from Good g Order By g.cost Desc")
    public Iterable<Good> findAllDesc();

    @Query(value = "Select g from Good g Order By g.cost Asc")
    public Iterable<Good> findAllAsc();

    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Desc")
    public Iterable<Good> findByCategoriesDesc(Set<Category> categories);

    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Asc")
    public Iterable<Good> findByCategoriesAsc(Set<Category> categories);
}
