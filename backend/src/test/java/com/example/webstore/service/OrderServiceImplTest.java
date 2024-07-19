package com.example.webstore.service;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.NotEnoughGoodException;
import com.example.webstore.model.Good;
import com.example.webstore.model.Order;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.requests.GoodQuantity;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private GoodServiceImpl goodService;

    @Mock
    private MailService mailService;

    @Mock
    private Authentication auth;

    @BeforeEach
    void initSecurityContext() {
        when(auth.getName()).thenReturn("test.test@test.test");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }
    
    @Test
    void createTestOK() {
        GoodQuantity goodQuantity1 = new GoodQuantity(1, 15);
        GoodQuantity goodQuantity2 = new GoodQuantity(2, 10);
        GoodQuantity goodQuantity3 = new GoodQuantity(3, 3);

        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test");
        Good good2 = new Good("test2", 1000, 0, 10, "test", "test", "test");
        Good good3 = new Good("test3", 1000, 0, 4, "test", "test", "test");

        User user = new User("Test", "Test", "test.test@test.test", "12345", new Role("USER"));
        List<GoodQuantity> goodQuantities = Arrays.asList(goodQuantity3, goodQuantity2, goodQuantity1);

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenAnswer(i -> i.getArguments()[0]);
        Mockito.when(userService.getByEmail("test.test@test.test")).thenReturn(user);
        Mockito.when(goodService.findById(1)).thenReturn(Optional.of(good1));
        Mockito.when(goodService.findById(2)).thenReturn(Optional.of(good2));
        Mockito.when(goodService.findById(3)).thenReturn(Optional.of(good3));  
        try {
            Order testOrder = orderService.createOrderAndSendMail(goodQuantities);
            Assertions.assertNotNull(testOrder);
            Assertions.assertEquals(user, testOrder.getUser());
            Assertions.assertEquals(new Date(System.currentTimeMillis()).toString(), testOrder.getDate().toString());
            Assertions.assertEquals(3, testOrder.getOrderDetails().size());
        } catch (Exception e) {
            
        }
    }

    @Test
    void createTestNotFound() {
        GoodQuantity goodQuantity1 = new GoodQuantity(1, 15);
        List<GoodQuantity> goodQuantities = Arrays.asList(goodQuantity1);
        User user = new User("Test", "Test", "fail.test@test.test", "12345", new Role("USER"));
        Mockito.when(userService.getByEmail("test.test@test.test")).thenReturn(user);
        Assertions.assertThrows(GoodNotFoundException.class, () -> orderService.createOrderAndSendMail(goodQuantities));
    }

    @Test
    void createTestUnavailable() {
        Good good1 = new Good("test1", 1000, 0, 2, "test", "test", "test");
        GoodQuantity goodQuantity1 = new GoodQuantity(1, 500);
        List<GoodQuantity> goodQuantities = Arrays.asList(goodQuantity1);
        User user = new User("Test", "Test", "fail.test@test.test", "12345", new Role("USER"));
        Mockito.when(userService.getByEmail("test.test@test.test")).thenReturn(user);
        Mockito.when(goodService.findById(1)).thenReturn(Optional.of(good1));
        Assertions.assertThrows(NotEnoughGoodException.class, () -> orderService.createOrderAndSendMail(goodQuantities));
    }
}
