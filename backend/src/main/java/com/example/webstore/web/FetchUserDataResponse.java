package com.example.webstore.web;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс ответа пользователю при восстановлении авторизации
 */
@Data
@AllArgsConstructor
public class FetchUserDataResponse {
    String username;
    String email;
}
