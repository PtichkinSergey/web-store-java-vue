package com.example.webstore.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.responses.JwtAuthenticationResponse;
import com.example.webstore.requests.SignInRequest;
import com.example.webstore.requests.SignUpRequest;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @InjectMocks
    private AuthenticationService authenticationService;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private RoleServiceImpl roleService;
    @Mock
    private JWTService jwtService;
    @Mock 
    private PasswordEncoder passwordEncoder;
    @Mock 
    private AuthenticationManager authenticationManager;

    @Test 
    void signUpTestOk() {
        SignUpRequest request = new SignUpRequest();
        request.setFirstName("test");
        request.setSecondName("test");
        request.setEmail("test.test@test.test");
        request.setPassword("1234");
        Role role = new Role("USER");
        User user = new User(request.getFirstName(), request.getSecondName(), request.getEmail(), "1234", role);
        Mockito.when(userService.getByEmail(Mockito.anyString())).thenThrow(new UsernameNotFoundException(""));
        Mockito.when(roleService.findByName("USER")).thenReturn(Optional.of(role));
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenAnswer(i -> i.getArgument(0).toString());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenAnswer(i -> i.getArgument(0));
        JwtAuthenticationResponse response = authenticationService.signUp(request);
        Assertions.assertEquals(user.toString(), response.getJwt());
        Assertions.assertEquals(user.getUsername(), response.getUsername());
    }

    @Test 
    void signInTestOk() {
        SignInRequest request = new SignInRequest();
        request.setEmail("test.test@test.test");
        request.setPassword("1234");
        User user = new User("Test", "Test", request.getEmail(), "1234", new Role("USER"));
        Mockito.when(userService.getByEmail("test.test@test.test")).thenReturn(user);
        Mockito.when(jwtService.generateToken(Mockito.any(User.class))).thenAnswer(i -> i.getArgument(0).toString());
        JwtAuthenticationResponse response = authenticationService.signIn(request);
        Assertions.assertEquals(user.toString(), response.getJwt());
        Assertions.assertEquals(user.getUsername(), response.getUsername());
    }
}
