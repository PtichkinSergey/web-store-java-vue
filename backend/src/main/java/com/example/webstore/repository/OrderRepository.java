package com.example.webstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.webstore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Integer>{
    
}
