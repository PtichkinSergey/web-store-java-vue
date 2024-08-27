package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации запроса неизвестной категории
 */
public class UnknownCategoryException extends RuntimeException {
    public UnknownCategoryException(String message) {
        super(message);
    }
}