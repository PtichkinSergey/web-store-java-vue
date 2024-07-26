package com.example.webstore.service.order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.webstore.exceptions.GoodNotFoundException;
import com.example.webstore.exceptions.NotEnoughGoodException;
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
    @Override
    public Order createOrderAndSendMail(List<GoodQuantity> goodQuantities) throws NotEnoughGoodException, GoodNotFoundException, UnauthorizedUserException, MailException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			User user = userService.getByEmail(authentication.getName());
            Order newOrder = new Order(user, new Date(System.currentTimeMillis()));
            StringBuilder message = new StringBuilder();
            message.append("Ваш заказ от ");
            message.append(newOrder.getDate());
            message.append(": \n\n");
            int orderAmount = 0;
            Set<OrderDetail> orderDetails = newOrder.getOrderDetails();
            List<Good> updatedGoods = new ArrayList<>();
            for (GoodQuantity goodQuantity : goodQuantities) {
                int goodId = goodQuantity.getGoodId();
                Optional<Good> good = goodService.findById(goodId);
                if(good.isPresent()) {
                    int goodCount = good.get().getCount();
                    int quantity = goodQuantity.getQuantity();
                    int cost = good.get().getCost();
                    if(quantity < 1) {
                        continue;
                    }
                    if(goodCount - quantity >= 0) {
                        good.get().setCount(goodCount - quantity);
                        updatedGoods.add(good.get());
                        orderDetails.add(new OrderDetail(newOrder, good.get(), quantity));
                        message.append(good.get().getName());
                        message.append(": ");
                        message.append(quantity);
                        message.append(" * ");
                        message.append(cost);
                        float discount = good.get().getDiscount();
                        if(discount > 0) {
                            message.append("- ");
                            message.append((int)(discount * 100));
                            message.append("% ");
                            message.append(" = ");
                            message.append(Math.ceil(quantity * cost * (1 - discount)));
                            message.append(" руб.\n");
                            orderAmount += Math.ceil(quantity * cost * (1 - discount));
                        }
                        else {
                            message.append(" = ");
                            message.append(quantity * cost);
                            message.append(" руб.\n");
                            orderAmount += quantity * cost;
                        }
                    }
                    else {
                        throw new NotEnoughGoodException(String.format("Товара с id: %s недостаточно на складе для осуществления заказа!", goodId));
                    }                    
                }
                else {
                    throw new GoodNotFoundException(String.format("Товар с id: %s не найден!", goodId));
                }
            }
            message.append("\nИтого: ");
            message.append(orderAmount);
            message.append(" руб.\n\n");
            message.append("Спасибо за то, что выбрали наш магазин!!!");
            newOrder.setOrderDetails(orderDetails);
            goodService.updateAll(updatedGoods);
            mailService.sendMail(authentication, message.toString());
            return orderRepository.save(newOrder);
		}
        else {
            throw new UnauthorizedUserException("Пользователь не авторизован!");
        }
    }

    @Override
    public List<Order> readAll() {
        return (List<Order>)orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
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
