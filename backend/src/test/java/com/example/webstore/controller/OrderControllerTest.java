package com.example.webstore.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.webstore.model.Good;
import com.example.webstore.model.Order;
import com.example.webstore.model.OrderDetail;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.requests.GoodQuantity;
import com.example.webstore.service.order.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderServiceImpl orderService;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void initSecurityContext() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllOrdersTest() throws Exception{
        User user = new User("user", "user", "test.test@test.test", "12345", new Role("USER"));
        Order order = new Order(user, new Timestamp(System.currentTimeMillis()));
        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test", 0);
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        orderDetails.add(new OrderDetail(order, good1, 1));
        order.setOrderDetails(orderDetails);
        List<Order> orders = Arrays.asList(order);
        when(orderService.readAll()).thenReturn(orders);
        mockMvc.perform(get("/api/orders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").exists());
        verify(orderService, times(1)).readAll();
    }

    @Test
    void getOrderByIdTest() throws Exception{
        User user = new User("user", "user", "test.test@test.test", "12345", new Role("USER"));
        Order order = new Order(user, new Timestamp(System.currentTimeMillis()));
        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test", 0);
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        orderDetails.add(new OrderDetail(order, good1, 1));
        order.setOrderDetails(orderDetails);
        when(orderService.findById(1)).thenReturn(order);
        mockMvc.perform(get("/api/orders/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.username").value(user.getUsername()))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.orderDetails").exists());
        verify(orderService, times(1)).findById(Mockito.anyInt());
    }

    @Test
    void orderCreateTest() throws Exception {
        GoodQuantity goodQuantity1 = new GoodQuantity(1, 1);
        GoodQuantity goodQuantity2 = new GoodQuantity(2, 2);
        GoodQuantity goodQuantity3 = new GoodQuantity(3, 3);
        Good good1 = new Good("test1", 1000, 0, 22, "test", "test", "test", 0);
        Good good2 = new Good("test2", 1000, 0, 10, "test", "test", "test", 0);
        Good good3 = new Good("test3", 1000, 0, 4, "test", "test", "test", 0);
        List<GoodQuantity> goodQuantities = Arrays.asList(goodQuantity1, goodQuantity2, goodQuantity3);
        String goodsJson = objectMapper.writeValueAsString(goodQuantities);

        User user = new User("user", "user", "test.test@test.test", "12345", new Role("USER"));
        Order order = new Order(user, new Timestamp(System.currentTimeMillis()));
        Set<OrderDetail> orderDetails = order.getOrderDetails();
        orderDetails.add(new OrderDetail(order, good1, 1));
        orderDetails.add(new OrderDetail(order, good2, 2));
        orderDetails.add(new OrderDetail(order, good3, 3));
        order.setOrderDetails(orderDetails);
        when(orderService.createOrderAndSendMail(goodQuantities)).thenReturn(order);
        mockMvc.perform(post("/api/order-create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(goodsJson))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.user.username").value(user.getUsername()))
        .andExpect(jsonPath("$.timestamp").exists())
        .andExpect(jsonPath("$.orderDetails").exists());
    }
    
}
