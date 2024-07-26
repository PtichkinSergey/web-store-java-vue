package com.example.webstore.service.authentication;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.webstore.responses.JwtAuthenticationResponse;
import com.example.webstore.service.jwt.JWTService;
import com.example.webstore.service.role.RoleServiceImpl;
import com.example.webstore.service.user.UserServiceImpl;
import com.example.webstore.requests.SignUpRequest;

import com.example.webstore.requests.SignInRequest;
import com.example.webstore.model.User;
import com.example.webstore.model.Role;

/**
 * Сервис для регистрации и авторизации пользователей. 
 * Внедряемые зависимости:
 * userService - сервис для работы с пользователями
 * roleService - сервис для работы с ролями
 * jwtService - сервис для работы с jwt
 * passwordEncoder - кодировщик паролей
 * authenticationManager - менеджер аутентификации
 */
@Service
public class AuthenticationService {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserServiceImpl userService, 
        RoleServiceImpl roleService, 
        JWTService jwtService, 
        PasswordEncoder passwordEncoder, 
        AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Optional<Role> role = roleService.findByName("USER");
        if (role.isPresent()) {
            User user = new User(request.getFirstName(), request.getSecondName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), role.get());
            try {
                if(userService.getByEmail(request.getEmail()) != null) {
                    return new JwtAuthenticationResponse(null, null, "Пользователь с таким адресом уже существует!");
                }
            } catch (UsernameNotFoundException e) {
                userService.create(user);
                String jwt = jwtService.generateToken(user);
                return new JwtAuthenticationResponse(jwt, user.getUsername(), null);
            }
        }
        return new JwtAuthenticationResponse(null, null, "Ошибка регистрации!");
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
        return new JwtAuthenticationResponse(jwtService.generateToken(user), user.getUsername(), null);
    }
}
