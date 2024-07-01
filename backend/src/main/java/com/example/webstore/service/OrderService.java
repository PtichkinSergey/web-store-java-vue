package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import com.example.webstore.model.Good;
import com.example.webstore.model.Order;
import com.example.webstore.web.OrderGood;

public interface OrderService {
    public Order create(Order order);
    public List<Order> readAll();
    public Optional<Order> findById(int id);
    public Order update(Order order);
    public void delete(int id);
    public void sendMail(List<OrderGood> orderGoods);
    public Good assignOrderToGood(Integer orderId, Integer goodId);
}
