package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.webstore.model.Role;

@SpringBootTest
@ActiveProfiles("test")
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    void clearDB() {
        roleRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Role role = roleRepository.save(new Role("testRole"));
        assertTrue(role.getId() > 0);
        roleRepository.deleteById(role.getId());
    }

    @Test
    void findByIdTest() {
        Role role = roleRepository.save(new Role("testRole"));
        assertEquals(role.getId(), roleRepository.findById(role.getId()).get().getId());
        roleRepository.deleteById(role.getId());
    }

    @Test
    void findByNameTest() {
        Role role = roleRepository.save(new Role("testRole"));
        assertEquals("testRole", roleRepository.findByName("testRole").get().getName());
        roleRepository.deleteById(role.getId());
    }

    @Test
    void findAllTest() {
        roleRepository.save(new Role("role1"));
        roleRepository.save(new Role("role2"));
        roleRepository.save(new Role("role3"));
        List<Role> roleList = new ArrayList<>();
        roleRepository.findAll().forEach(roleList::add);
        assertEquals(3, roleList.size());
    }

    @Test
    void deleteByIdTest() {
        Role role1 = roleRepository.save(new Role("testRole1"));
        roleRepository.deleteById(role1.getId());
        assertFalse(roleRepository.findById(role1.getId()).isPresent());
    }
}
