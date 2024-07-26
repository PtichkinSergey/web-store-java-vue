package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.webstore.model.Category;

// Репозиторий для доступа к данным таблицы сущности категории
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{
    @NonNull
    public Optional<Category> findById(@NonNull Integer id);

    @NonNull
    public Iterable<Category> findAll();

    @NonNull
    public Category save(@NonNull Category category);

    public void deleteById(@NonNull Integer id);
}
