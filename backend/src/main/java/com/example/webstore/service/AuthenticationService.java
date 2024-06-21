package com.example.webstore.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.webstore.web.JwtAuthenticationResponse;
import com.example.webstore.web.SignUpRequest;

import lombok.AllArgsConstructor;

import com.example.webstore.web.SignInRequest;
import com.example.webstore.model.User;
import com.example.webstore.model.Role;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final UserServiceImpl userService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        User user = new User(request.getFirstName(), request.getSecondName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), Role.USER);
        if(userService.getByEmail(request.getEmail()) != null){
            return new JwtAuthenticationResponse(null, null, "Пользователь с таким адресом уже существует!");
        }
        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getUsername(), null);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            ));
        } catch (Exception e) {
            return new JwtAuthenticationResponse(null, null, "Неверный логин или пароль!");
        }
        
        User user = userService.getByEmail(request.getEmail());
        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getUsername(), null);
    }
}
