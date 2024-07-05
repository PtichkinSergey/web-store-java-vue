package com.example.webstore.web;

import lombok.Data;

/**
 * Запрос на авторизацию
 */
@Data
public class SignInRequest {

    private String email;
    private String password;
}
