package com.github.onlineshop.dto.response;

import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

// Информация о пользователе
@Data
@AllArgsConstructor
public class UserInfoResponse {
    private User user;
    private Page<Order> orders;
}
