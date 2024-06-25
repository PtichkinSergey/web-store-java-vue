package com.example.webstore.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import org.springframework.stereotype.Service;

import com.example.webstore.model.User;

import io.jsonwebtoken.impl.lang.Function;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;


    public String generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(6, ChronoUnit.HOURS)) // настройка времени действия токена
                .subject(user.getEmail())
                .claim("id", user.getId())
                .claim("name", user.getFirstName() + " " + user.getSecondName())
                .claim("role", user.getRole())
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Извлечение имени пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    public String extractEmail(String token) {
        return extractClaim(token, Jwt::getSubject);
    }

    /**
     * Извлечение данных из токена
     *
     * @param token           токен
     * @param claimsResolvers функция извлечения данных
     * @param <T>             тип данных
     * @return данные
     */
    private <T> T extractClaim(String token, Function<Jwt, T> claimsResolvers) {
        final Jwt claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     * Извлечение даты истечения токена
     *
     * @param token токен
     * @return дата истечения
     */
    private Instant extractExpiration(String token) {
        return extractClaim(token, Jwt::getExpiresAt);
    }

    /**
     * Извлечение всех данных из токена
     *
     * @param token токен
     * @return данные
     */
    private Jwt extractAllClaims(String token) {
        return this.decoder.decode(token);
    }

    /**
     * Проверка токена на валидность
     *
     * @param token       токен
     * @param user данные пользователя
     * @return true, если токен валиден
     */
    public boolean isTokenValid(String token, User user) {
        final String userName = extractEmail(token);
        return (userName.equals(user.getEmail())) && !isTokenExpired(token);
    }

    /**
     * Проверка токена на просроченность
     *
     * @param token токен
     * @return true, если токен просрочен
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).isBefore(Instant.now());
    }

}
