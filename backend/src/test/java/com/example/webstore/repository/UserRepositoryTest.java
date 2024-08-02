package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired 
    private RoleRepository roleRepository;

    private Role testRole;

    @BeforeEach
    void initDB() {
        roleRepository.save(new Role("USER"));
        testRole = roleRepository.findByName("USER").get();
    }

    @AfterEach
    void clearDB() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void saveTest() {
        User role = userRepository.save(new User("user", "user", "user.test@test.test", "12345", testRole));
        assertTrue(role.getId() > 0);
        userRepository.deleteById(role.getId());
    }

    @Test
    void findByIdTest() {
        User role = userRepository.save(new User("user", "user", "user.test@test.test", "12345", testRole));
        assertEquals(role.getId(), userRepository.findById(role.getId()).get().getId());
        userRepository.deleteById(role.getId());
    }

    @Test
    void findByNameTest() {
        User role = userRepository.save(new User("user", "user", "user.test@test.test", "12345", testRole));
        assertEquals("user.test@test.test", userRepository.findByEmail("user.test@test.test").get().getEmail());
        userRepository.deleteById(role.getId());
    }

    @Test
    void findAllTest() {
        userRepository.save(new User("user1", "user1", "user1.test@test.test", "12345", testRole));
        userRepository.save(new User("user2", "user2", "user2.test@test.test", "12345", testRole));
        userRepository.save(new User("user3", "user3", "user3.test@test.test", "12345", testRole));
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        assertEquals(3, userList.size());
    }

    @Test
    void deleteByIdTest() {
        User role1 = userRepository.save(new User("user", "user", "user.test@test.test", "12345", testRole));
        userRepository.deleteById(role1.getId());
        assertFalse(userRepository.findById(role1.getId()).isPresent());
    }
}
