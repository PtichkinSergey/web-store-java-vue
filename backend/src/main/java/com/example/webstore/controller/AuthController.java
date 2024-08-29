package com.example.webstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.responses.JwtAuthenticationResponse;
import com.example.webstore.security.authentication.AuthenticationService;
import com.example.webstore.exceptions.RoleNotFoundException;
import com.example.webstore.requests.SignInRequest;
import com.example.webstore.requests.SignUpRequest;

/**
 * Контроллер запросов на авторизацию/регистрацию.
 * Внедряемые зависимости:
 * authenticationService - сервис авторизации
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authentificationService) {
        this.authenticationService = authentificationService;
    }

    /**
     * Эндпоинт для запросов на регистрацию
     * 
     * @param request запрос на регистрацию
     * @return ответ содержащий jwt или сообщение об ошибке
     */
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        try {
            return authenticationService.signUp(request);
        } catch (RoleNotFoundException e) {
            return new JwtAuthenticationResponse(null, null, "Ошибка регистрации!");
        }
    }

    /**
     * Эндпоинт для запросов на авторизацию
     * 
     * @param request запрос на авторизацию
     * @return ответ содержащий jwt или сообщение об ошибке
     */
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

}