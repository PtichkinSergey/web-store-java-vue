package com.example.webstore.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.service.AuthentificationService;
import com.example.webstore.web.JwtAuthentificationResponse;
import com.example.webstore.web.SignInRequest;
import com.example.webstore.web.SignUpRequest;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthentificationService authentificationService;

    public AuthController(AuthentificationService authentificationService) {
        this.authentificationService = authentificationService;
    }

    @PostMapping("/sign-up")
    public JwtAuthentificationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        System.out.println(request.getFirstName() + " " + request.getSecondName() + " " + request.getEmail());
        return authentificationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthentificationResponse signIn(@RequestBody @Valid SignInRequest request) {
        System.out.println(request.getEmail());
        return authentificationService.signIn(request);
    }

}