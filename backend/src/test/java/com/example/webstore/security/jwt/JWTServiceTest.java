package com.example.webstore.security.jwt;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;

@ExtendWith(MockitoExtension.class)
class JWTServiceTest {
    @InjectMocks
    private JWTService jwtService;
    @Mock
    private JwtEncoder encoder;
    @Mock
    private JwtDecoder decoder;

    @Test
    void generateTokenTest() {
        User user1 = new User("Ivan", "Ivanov", "ivan.ivanov@mail.ru", "12345", new Role("USER"));
        Instant now = Instant.now();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Instant expired = calendar.getTime().toInstant();
        Map<String, Object> headers = new HashMap<>();
        headers.put("header", null);
        Mockito.when(encoder.encode(Mockito.any(JwtEncoderParameters.class)))
        .thenAnswer(i -> 
            new Jwt(
                ((JwtEncoderParameters) i.getArgument(0)).getClaims().getSubject(), 
                now, 
                expired, 
                headers, 
                ((JwtEncoderParameters) i.getArgument(0)).getClaims().getClaims()
            )
        );
        Assertions.assertEquals("ivan.ivanov@mail.ru", jwtService.generateToken(user1));
    }
}
