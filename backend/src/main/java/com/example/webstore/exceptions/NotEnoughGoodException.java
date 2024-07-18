package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда товара недостаточно на складе
 */
public class NotEnoughGoodException extends Exception{
    public NotEnoughGoodException(String message) {
        super(message);
    }
}