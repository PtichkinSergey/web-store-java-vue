package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.webstore.model.Order;
import com.example.webstore.web.GoodQuantity;

public interface OrderService {
    public ResponseEntity<Order> create(List<GoodQuantity> goodQuantities);
    public List<Order> readAll();
    public Optional<Order> findById(int id);
    public Order update(Order order);
    public void delete(int id);
    public void sendMail(Order order);
}
