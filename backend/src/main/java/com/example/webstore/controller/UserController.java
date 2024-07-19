package com.example.webstore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.User;
import com.example.webstore.service.UserServiceImpl;
import com.example.webstore.responses.FetchUserDataResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

/** 
 * Контроллер для доступа к данным пользователя
 * Внедряемые сущности:
 * userService - сервис пользователей
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserServiceImpl userService;

	public UserController(UserServiceImpl userService) {
		this.userService = userService;
	}

	/**
	 * Эндпоинт для востановления авторизации пользователя, имеющего токен авторизации в localStorage
	 * @return Данные о имени пользователя и email
	 */
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/auth_user")
	public FetchUserDataResponse getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			User user = userService.getByEmail(authentication.getName());
			return new FetchUserDataResponse(user.getUsername(), user.getEmail());
		}
		return new FetchUserDataResponse(null, null);
	}
	
}
