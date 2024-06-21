package com.example.webstore.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private String jwt;
    private String username;
    private String errorText;
}
