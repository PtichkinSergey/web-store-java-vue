package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда пользователь не авторизован
 */
public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
