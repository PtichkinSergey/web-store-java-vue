package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда товар не найден
 */
public class GoodNotFoundException extends RuntimeException {
    public GoodNotFoundException(String message) {
        super(message);
    }
}
