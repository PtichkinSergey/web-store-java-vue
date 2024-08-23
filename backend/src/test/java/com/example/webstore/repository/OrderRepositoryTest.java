package com.example.webstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.webstore.model.Order;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;

@SpringBootTest
@ActiveProfiles("test")
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User testUser;
    private Date now;

    @BeforeEach
    void initDB() {
        roleRepository.save(new Role("USER"));
        testUser = userRepository.save(new User("Test", "Test", "test.test@test.test", "12345", roleRepository.findByName("USER").get()));
        now = new Date(System.currentTimeMillis());
    }

    @AfterEach
    void clearDB() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void saveTest() {
        Order order = orderRepository.save(new Order(testUser, now));
        assertTrue(order.getId() > 0);
        orderRepository.deleteById(order.getId());
    }

    @Test
    void findByIdTest() {
        Order order = orderRepository.save(new Order(testUser, now));
        assertEquals(order.getId(), orderRepository.findById(order.getId()).get().getId());
        orderRepository.deleteById(order.getId());
    }

    @Test
    void findAllTest() {
        Order order1 = new Order(testUser, now);
        Order order2 = new Order(testUser, now);
        Order order3 = new Order(testUser, now);
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        assertEquals(3, orderList.size());
    }

    @Test
    void deleteByIdTest() {
        Order order = orderRepository.save(new Order(testUser, now));
        orderRepository.deleteById(order.getId());
        assertFalse(orderRepository.findById(order.getId()).isPresent());
    }
}
