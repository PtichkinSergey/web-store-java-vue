package com.example.webstore.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.webstore.model.User;
import com.example.webstore.service.JWTService;
import com.example.webstore.service.UserServiceImpl;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";
    private final JWTService jwtService;
    private final UserServiceImpl userService;

    public JwtAuthenticationFilter(@Lazy JWTService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Получаем токен из заголовка
        String authHeader = request.getHeader(HEADER_NAME);
        if (authHeader.isEmpty() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Обрезаем префикс и получаем имя пользователя из токена
        String jwt = authHeader.substring(BEARER_PREFIX.length());
        String email = "";
        try {
            email = jwtService.extractEmail(jwt);
        } catch (JwtValidationException e) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if (email.length() > 0 && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = null;
            try {
                user = userService.getByEmail(email);
            } catch (UsernameNotFoundException e) {
                return;
            }   
            // Если токен валиден, то аутентифицируем пользователя
            if (jwtService.isTokenValid(jwt, user)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
            else {
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
