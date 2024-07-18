package com.example.webstore.web;
import lombok.Data;

/**
 * Класс ответа пользователю при восстановлении авторизации
 */
@Data
public class FetchUserDataResponse {
    String username;
    String email;
    
    public FetchUserDataResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
