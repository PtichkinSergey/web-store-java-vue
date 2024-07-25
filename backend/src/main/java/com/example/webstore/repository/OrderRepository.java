package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import com.example.webstore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
    @NonNull
    @Query(value = "Select o from Order o where o.id = :id")
    public Optional<Order> findById(@NonNull Integer id);

    @NonNull
    @Query(value = "Select o from Order o")
    public Iterable<Order> findAll();

    @NonNull
    public Order save(@NonNull Order order);

    @Query(value = "Delete from Order o where o.id = :id")
    public void deleteById(@NonNull Integer id);
}
