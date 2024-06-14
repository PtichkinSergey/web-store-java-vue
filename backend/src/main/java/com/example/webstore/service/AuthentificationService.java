package com.example.webstore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.webstore.web.JwtAuthentificationResponse;
import com.example.webstore.web.SignUpRequest;

import lombok.AllArgsConstructor;

import com.example.webstore.web.SignInRequest;
import com.example.webstore.model.User;

@AllArgsConstructor
@Service
public class AuthentificationService {
    private final UserServiceImpl userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authentificationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthentificationResponse signUp(SignUpRequest request) {
        User user = new User(request.getFirstName(), request.getSecondName(), request.getEmail(), passwordEncoder.encode(request.getPassword()) , false);
        userService.create(user);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthentificationResponse(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthentificationResponse signIn(SignInRequest request) {
        authentificationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        User user = userService.getByEmail(request.getEmail());

        String jwt = jwtService.generateToken(user);
        return new JwtAuthentificationResponse(jwt);
    }
}
