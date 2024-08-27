package com.example.webstore.service.order;

import java.util.List;

import com.example.webstore.model.Order;
import com.example.webstore.requests.GoodQuantity;

public interface OrderService {
    public Order createOrderAndSendMail(List<GoodQuantity> goodQuantities);
    public List<Order> readAll();
    public Order findById(int id);
    public Order update(Order order);
    public void delete(int id);
}
