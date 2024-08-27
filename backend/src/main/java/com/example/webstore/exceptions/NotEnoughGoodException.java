package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда товара недостаточно на складе
 */
public class NotEnoughGoodException extends RuntimeException {
    public NotEnoughGoodException(String message) {
        super(message);
    }
}