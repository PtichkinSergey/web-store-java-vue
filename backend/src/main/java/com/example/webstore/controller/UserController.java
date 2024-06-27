package com.example.webstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import com.example.webstore.model.User;
import com.example.webstore.service.UserServiceImpl;
import com.example.webstore.web.FetchUserDataResponse;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
			List<User> users = new ArrayList<User>();
			userService.readAll().forEach(users::add);
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

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
