package com.example.webstore.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.user.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Test
    void getByEmailTest() {
        User user1 = new User("Ivan", "Ivanov", "ivan.ivanov@mail.ru", "12345", new Role("USER"));
        User user2 = new User("Petr", "Petrov", "piterP@gmail.com", "password", new Role("USER"));
        String email1 = "ivan.ivanov@mail.ru";
        String email2 = "piterP@gmail.com";
        Mockito.when(userRepository.findByEmail(email1)).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(email2)).thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByEmail("")).thenReturn(Optional.empty());
        Assertions.assertEquals(user1, userService.getByEmail(email1));
        Assertions.assertEquals(user2, userService.getByEmail(email2));
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getByEmail(""));
    }
}
