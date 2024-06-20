package com.example.webstore.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.service.AuthenticationService;
import com.example.webstore.web.JwtAuthenticationResponse;
import com.example.webstore.web.SignInRequest;
import com.example.webstore.web.SignUpRequest;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authentificationService) {
        this.authenticationService = authentificationService;
    }

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        System.out.println(request.getFirstName() + " " + request.getSecondName() + " " + request.getEmail());
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        System.out.println("AUTH:" + request.getEmail());
        return authenticationService.signIn(request);
    }

}