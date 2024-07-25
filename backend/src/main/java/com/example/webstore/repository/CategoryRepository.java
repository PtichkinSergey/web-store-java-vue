package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.example.webstore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    @NonNull
    @Query(value = "Select c from Category c where c.id = :id")
    public Optional<Category> findById(@NonNull Integer id);

    @NonNull
    @Query(value = "Select c from Category c")
    public Iterable<Category> findAll();

    @NonNull
    // @Query(value = 
    //     "Insert into Category (name, parent_id) " + 
    //     "select :category.getName(), :category.getParentId()"
    // )
    public Category save(@NonNull Category category);

    @Query(value = "Delete from Category c where c.id = :id")
    public void deleteById(@NonNull Integer id);
}
