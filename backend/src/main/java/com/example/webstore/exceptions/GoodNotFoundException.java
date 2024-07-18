package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда товар не найден
 */
public class GoodNotFoundException extends Exception {
    public GoodNotFoundException(String message) {
        super(message);
    }
}
