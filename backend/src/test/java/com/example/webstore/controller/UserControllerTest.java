package com.example.webstore.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock 
    private UserServiceImpl userService;

    private MockMvc mockMvc;

    @Mock
    private Authentication auth;

    @BeforeEach
    void initSecurityContext() {
        when(auth.getName()).thenReturn("test.test@test.test");
        SecurityContextHolder.getContext().setAuthentication(auth);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getAuthenticatedUserTest() throws Exception {
        User user1 = new User("user1", "user1", "test.test@test.test", "12345", Role.USER);
        when(userService.getByEmail("test.test@test.test")).thenReturn(user1);
        mockMvc.perform(get("/api/auth_user"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value(user1.getUsername()))
        .andExpect(jsonPath("$.email").value(user1.getEmail()));
        verify(userService, times(1)).getByEmail(Mockito.anyString());
    }
}
