package com.example.webstore.responses;

import lombok.Data;

/**
 * Класс ответа пользователю при авторизации/регистрации
 */
@Data
public class JwtAuthenticationResponse {
    private String jwt;
    private String username;
    private String errorText;

    public JwtAuthenticationResponse(String jwt, String username, String errorText) {
        this.jwt = jwt;
        this.username = username;
        this.errorText = errorText;
    } 
}
