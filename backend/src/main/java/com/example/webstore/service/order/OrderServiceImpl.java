package com.example.webstore.service.order;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webstore.exceptions.NotEnoughGoodException;
import com.example.webstore.exceptions.OrderNotFoundException;
import com.example.webstore.exceptions.UnauthorizedUserException;
import com.example.webstore.model.Good;
import com.example.webstore.model.Order;
import com.example.webstore.model.OrderDetail;
import com.example.webstore.model.User;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.requests.GoodQuantity;
import com.example.webstore.service.good.GoodServiceImpl;
import com.example.webstore.service.mail.MailService;
import com.example.webstore.service.user.UserServiceImpl;

/**
 * Класс сервиса для работы с заказами. Внедряемые зависимости: 
 * orderRepository - crud репозиторий
 * userService - сервис для работы с пользователями
 * goodService - сервис для работы с товарами
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MailService mailService;
    private final UserServiceImpl userService;
    private final GoodServiceImpl goodService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, MailService mailService, UserServiceImpl userService, GoodServiceImpl goodService) {
        this.orderRepository = orderRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.goodService = goodService;
    }

    /**
     * Метод создания заказа из сущностей GoodQuantity, содержащих id товара и их количество
     * Возвращает созданный объект заказа
     */
    @Transactional
    @Override
    public Order createOrderAndSendMail(List<GoodQuantity> goodQuantities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
            String emailBuyer = authentication.getName(); 
            User user = userService.getByEmail(emailBuyer);
            Order newOrder = new Order(user, new Timestamp(System.currentTimeMillis()));
            String pattern = "dd.MM.yyyy HH:mm";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            StringBuilder message = new StringBuilder();
            message.append("Ваш заказ от ").append(dateFormatter.format(newOrder.getTimestamp().toLocalDateTime())).append(": \n\n");
            int orderAmount = 0;
            Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
            List<Good> updatedGoods = new ArrayList<>();
            for (GoodQuantity goodQuantity : goodQuantities) {
                int goodId = goodQuantity.getGoodId();
                Good good = goodService.findById(goodId);
                int goodCount = good.getCount();
                int quantity = goodQuantity.getQuantity();
                int cost = good.getCost();
                if(quantity < 1) {
                    continue;
                }
                if(goodCount - quantity >= 0) {
                    good.setCount(goodCount - quantity);
                    updatedGoods.add(good);
                    orderDetails.add(new OrderDetail(newOrder, good, quantity));
                    message.append(good.getName()).append(": ").append(quantity).append(" * ").append(cost);
                    float discount = good.getDiscount();
                    if(discount > 0) {
                        double orderPositionCost = Math.ceil(quantity * cost * (1 - discount));
                        message.append("- ").append((int)(discount * 100)).append("% ").append(" = ").append(orderPositionCost).append(" руб.\n");
                        orderAmount += orderPositionCost;
                    } else {
                        message.append(" = ").append(quantity * cost).append(" руб.\n");
                        orderAmount += quantity * cost;
                    }
                } else {
                    throw new NotEnoughGoodException(String.format("Товара с id: %s недостаточно на складе для осуществления заказа!", goodId));
                }                    
            }
            message.append("\nИтого: ").append(orderAmount).append(" руб.\n\n").append("Спасибо за то, что выбрали наш магазин!!!");
            newOrder.setOrderDetails(orderDetails);
            goodService.updateAll(updatedGoods);
            mailService.sendMail(emailBuyer, message.toString());
            return orderRepository.save(newOrder);
		} else {
            throw new UnauthorizedUserException("Пользователь не авторизован!");
        }
    }

    @Override
    public List<Order> readAll() {
        List<Order> orderList = new ArrayList<>();
        orderRepository.findAll().forEach(orderList::add);
        return orderList;
    }

    @Override
    public Order findById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new OrderNotFoundException(String.format("Заказ с id: %s не найден!", id));
        }
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
