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

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.NotEnoughGoodException;
import com.example.webstore.exceptions.UnauthorizedUserException;
import com.example.webstore.model.Order;
import com.example.webstore.requests.GoodQuantity;
import com.example.webstore.service.order.OrderServiceImpl;

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
@RequestMapping("/api")
public class OrderController {
    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    /**
     * Эндпоинт для доступа к всем заказам
     * @return Список всех заказов
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orderList = orderService.readAll();
            if (orderList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    @PostMapping(path = "/order-create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> orderCreate(@RequestBody @Valid List<GoodQuantity> goodQuantities) {
        try {
            return new ResponseEntity<>(orderService.createOrderAndSendMail(goodQuantities), HttpStatus.CREATED);
        } catch (NotEnoughGoodException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        } catch (GoodNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (MailSendException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (MailAuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (MailParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
