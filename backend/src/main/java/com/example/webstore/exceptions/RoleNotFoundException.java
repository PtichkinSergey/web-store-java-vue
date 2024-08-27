package com.example.webstore.exceptions;

/**
 * Исключение для обработки ситуации, когда роль не найдена
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}