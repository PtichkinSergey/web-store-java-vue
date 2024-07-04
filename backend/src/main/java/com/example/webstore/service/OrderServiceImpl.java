package com.example.webstore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Good;
import com.example.webstore.model.Order;
import com.example.webstore.model.OrderDetail;
import com.example.webstore.model.User;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.web.GoodQuantity;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final MailSender mailSender;
    private final UserServiceImpl userService;
    private final GoodServiceImpl goodService;

    @Override
    public ResponseEntity<Order> create(List<GoodQuantity> goodQuantities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			User user = userService.getByEmail(authentication.getName());
            Order newOrder = new Order(user, new Date(System.currentTimeMillis()));
            Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
            List<Good> updatedGoods = new ArrayList<Good>();
            Iterator<GoodQuantity> iter = goodQuantities.iterator();
            while (iter.hasNext()) {
                GoodQuantity goodQuantity = iter.next();
                Optional<Good> good = goodService.findById(goodQuantity.getGoodId());
                if(good.isPresent()) {
                    int goodCount = good.get().getCount();
                    if(goodQuantity.getGoodQuantity() < 1) {
                        continue;
                    }
                    if(goodCount - goodQuantity.getGoodQuantity() >= 0) {
                        good.get().setCount(goodCount - goodQuantity.getGoodQuantity());
                        updatedGoods.add(good.get());
                        orderDetails.add(new OrderDetail(newOrder, good.get(), goodQuantity.getGoodQuantity()));
                    }
                    else {
                        return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
                    }                    
                }
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            newOrder.setOrderDetails(orderDetails);
            goodService.updateAll(updatedGoods);
            return new ResponseEntity<Order>(orderRepository.save(newOrder), HttpStatus.OK);
		}
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public List<Order> readAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }
    
    @Override 
    public void sendMail(Order order) {
        String subject = "Заказ в интернет магазине";
        StringBuilder message = new StringBuilder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
            final SimpleMailMessage simpleMail = new SimpleMailMessage();
            simpleMail.setFrom("sergey.ptichkin@gmail.com");
            simpleMail.setTo(authentication.getName());
            simpleMail.setSubject(subject);
            message.append("Ваш заказ от " + order.getDate() + ": \n\n");
            Iterator<OrderDetail> iter = order.getOrderDetails().iterator();
            int orderAmount = 0;
            while (iter.hasNext()) {
                OrderDetail orderDetail = iter.next();
                message.append(orderDetail.getGood().getName() + ": " + orderDetail.getQuantity() + " * " + orderDetail.getGood().getCost());
                if(orderDetail.getGood().getDiscount() > 0) {
                    float discount = orderDetail.getGood().getDiscount();
                    message.append("- " + (int)(discount * 100) + "% ");
                    message.append(" = "  + Math.ceil(orderDetail.getQuantity() * orderDetail.getGood().getCost() * (1 - discount)) + " руб.\n");
                    orderAmount += Math.ceil(orderDetail.getQuantity() * orderDetail.getGood().getCost() * (1 - discount));
                }
                else {
                    message.append(" = " + orderDetail.getQuantity() * orderDetail.getGood().getCost() + " руб.\n");
                    orderAmount += orderDetail.getQuantity() * orderDetail.getGood().getCost();
                }
            }
            message.append("\nИтого: " + orderAmount + " руб.\n\n");
            message.append("Спасибо за то, что выбрали наш магазин!!!");
            simpleMail.setText(message.toString());
			this.mailSender.send(simpleMail);
		}
    }
}
