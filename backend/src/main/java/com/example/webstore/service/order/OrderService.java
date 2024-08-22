package com.example.webstore.service.order;

import java.util.List;

import org.springframework.mail.MailException;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.NotEnoughGoodException;
import com.example.webstore.exceptions.OrderNotFoundException;
import com.example.webstore.exceptions.UnauthorizedUserException;
import com.example.webstore.model.Order;
import com.example.webstore.requests.GoodQuantity;

public interface OrderService {
    public Order createOrderAndSendMail(List<GoodQuantity> goodQuantities) throws NotEnoughGoodException, GoodNotFoundException, UnauthorizedUserException, MailException;
    public List<Order> readAll();
    public Order findById(int id) throws OrderNotFoundException;
    public Order update(Order order);
    public void delete(int id);
}
