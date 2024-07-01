package com.example.webstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.webstore.model.Order;
import com.example.webstore.model.User;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.web.FetchUserDataResponse;
import com.example.webstore.web.OrderGood;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final MailSender mailSender;

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
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
    public void sendMail(List<OrderGood> orderGoods) {
        String subject = "Заказ в интернет магазине";
        String message = "Test message";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
            final SimpleMailMessage simpleMail = new SimpleMailMessage();
            simpleMail.setFrom("sergey.ptichkin@gmail.com");
            simpleMail.setTo(authentication.getName());
            simpleMail.setSubject(subject);
            simpleMail.setText(message);
			this.mailSender.send(simpleMail);
		}
    }
}
