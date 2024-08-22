package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда заказ не найден
 */
public class OrderNotFoundException extends Exception {
    public OrderNotFoundException(String message) {
        super(message);
    }
}