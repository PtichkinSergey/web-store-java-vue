package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации запроса неизвестной категории
 */
public class UnknownCategoryException extends Exception {
    public UnknownCategoryException(String message) {
        super(message);
    }
}