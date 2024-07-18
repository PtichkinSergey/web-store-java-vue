package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда пользователь не авторизован
 */
public class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
