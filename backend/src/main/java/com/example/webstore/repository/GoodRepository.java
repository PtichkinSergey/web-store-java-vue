package com.example.webstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.example.webstore.model.Good;
import com.example.webstore.model.Category;
import java.util.Set;


public interface GoodRepository extends CrudRepository<Good, Integer>{
    @NonNull
    @Query(value = "Select g from Good g where g.id = :id")
    public Optional<Good> findById(@NonNull Integer id);

    @NonNull
    // @Query(value = 
    //     "Insert into Good (name, cost, discount, count, manufacturer, description, image_path) " + 
    //     "select :good.getName(), :good.getCost(), :good.getDiscount(), :good.getCount(), :good.getManufacturer(), :good.getDescription(), :good.getImagePath()"
    // )
    public Good save(@NonNull Good good);

    @Query(value = "Delete from Good g where g.id = :id")
    public void deleteById(@NonNull Integer id);

    @Query(value = "Select g from Good g Order By g.cost Desc")
    public List<Good> findAllDesc();

    @Query(value = "Select g from Good g Order By g.cost Asc")
    public List<Good> findAllAsc();

    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Desc")
    public List<Good> findByCategoriesDesc(Set<Category> categories);

    @Query(value = "Select g from Good g join g.categories c Where c in :categories Order By g.cost Asc")
    public List<Good> findByCategoriesAsc(Set<Category> categories);
}
