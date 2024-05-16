package com.github.onlineshop.service;

import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    Order getOrder(Long orderId);

    List<Perfume> getOrdering();

    Page<Order> getUserOrdersList(Pageable pageable);

    Long postOrder(User user, OrderRequest orderRequest);
}
