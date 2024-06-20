package com.example.webstore.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.service.AuthenticationService;
import com.example.webstore.web.FetchUserDataRequest;
import com.example.webstore.web.JwtAuthenticationResponse;
import com.example.webstore.web.SignInRequest;
import com.example.webstore.web.SignUpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authentificationService) {
        this.authenticationService = authentificationService;
    }

    @GetMapping("/fetch-user-data")
    public String fetchUserData(@RequestBody @Valid FetchUserDataRequest request) {
        return this.authenticationService.fetchUserData(request);
    }

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

}