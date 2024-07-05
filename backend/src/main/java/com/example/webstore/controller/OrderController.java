package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.Order;
import com.example.webstore.service.OrderServiceImpl;
import com.example.webstore.web.GoodQuantity;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Контроллер запросов для доступа к данным заказов.
 * Внедряемые зависимости: 
 * orderService - сервис заказов
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class OrderController {
    private final OrderServiceImpl orderService;

    /**
     * Эндпоинт для доступа к всем заказам
     * @return Список всех заказов
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orderList = new ArrayList<Order>();
            orderService.readAll().forEach(orderList::add);
            if (orderList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Эндпоинт для доступа к заказу по id
     * @param id
     * @return Экземпляр заказа
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") int id) {
        Optional<Order> orderData = orderService.findById(id);
        if (orderData.isPresent()) {
            return new ResponseEntity<>(orderData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Эндпоинт для создания заказа
     * @param goodQuantities список сущностей GoodQuantity (id товара + их количество в заказе) 
     * @return Созданный заказ
     */
    @PostMapping("/order-create")
    public ResponseEntity<Order> orderCreate(@RequestBody @Valid List<GoodQuantity> goodQuantities) {
        ResponseEntity<Order> createResponse = orderService.create(goodQuantities);
        if(createResponse.getStatusCode() == HttpStatus.OK) {
            try {
                orderService.sendMail(createResponse.getBody());
            } catch (MailSendException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_GATEWAY);
            } catch (MailAuthenticationException e) {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            } catch (MailParseException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        return createResponse;
    }
}
