package com.example.webstore.web;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FetchUserDataResponse {
    String username;
    String email;
}
