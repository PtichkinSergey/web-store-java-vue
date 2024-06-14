package com.example.webstore.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.example.webstore.model.User;

@Service
public class JWTService {

    private final JwtEncoder encoder;

    public JWTService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS)) // настройка времени действия токена
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getFirstName() + " " + user.getSecondName())
                .claim("isAdmin", user.isAdmin())
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    // public String extractUserName(String token) {
    //     return extractClaim(token, Claims::getSubject);
    // }

    // /**
    //  * Проверка токена на валидность
    //  *
    //  * @param token       токен
    //  * @param userDetails данные пользователя
    //  * @return true, если токен валиден
    //  */
    // public boolean isTokenValid(String token, UserDetails userDetails) {
    //     final String userName = extractUserName(token);
    //     return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    // }

}
