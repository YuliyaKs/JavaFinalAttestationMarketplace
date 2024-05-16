package com.github.onlineshop.service.impl;

import com.github.onlineshop.constants.ErrorMessage;
import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.OrderRequest;
import com.github.onlineshop.repository.OrderRepository;
import com.github.onlineshop.service.OrderService;
import com.github.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Сервис для управления заказами
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    // Получить заказ по ID
    @Override
    public Order getOrder(Long orderId) {
        User user = userService.getAuthenticatedUser();
        return orderRepository.getByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND));
    }

    // Получить корзину пользователя
    @Override
    public List<Perfume> getOrdering() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    // Получить список заказов пользователя
    @Override
    public Page<Order> getUserOrdersList(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        return orderRepository.findOrderByUserId(user.getId(), pageable);
    }

    // Создать новый заказ
    @Override
    @Transactional
    public Long postOrder(User user, OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setUser(user);
        order.getPerfumes().addAll(user.getPerfumeList());
        orderRepository.save(order);
        user.getPerfumeList().clear();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("order", order);
        mailService.sendMessageHtml(order.getEmail(), "Order #" + order.getId(), "order-template", attributes);
        return order.getId();
    }
}
