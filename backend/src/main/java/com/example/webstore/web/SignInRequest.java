package com.example.webstore.web;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
