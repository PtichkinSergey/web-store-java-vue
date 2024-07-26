package com.example.webstore.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.webstore.model.Order;

// Репозиторий для доступа к данным таблицы сущности заказа
@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{
    @NonNull
    public Optional<Order> findById(@NonNull Integer id);

    @NonNull
    public Iterable<Order> findAll();

    @NonNull
    public Order save(@NonNull Order order);

    public void deleteById(@NonNull Integer id);
}
