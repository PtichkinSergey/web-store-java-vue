package com.example.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webstore.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
