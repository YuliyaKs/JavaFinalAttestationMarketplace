package com.github.onlineshop.service.impl;

import com.github.onlineshop.constants.ErrorMessage;
import com.github.onlineshop.constants.SuccessMessage;
import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.ChangePasswordRequest;
import com.github.onlineshop.dto.request.EditUserRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.repository.OrderRepository;
import com.github.onlineshop.repository.UserRepository;
import com.github.onlineshop.security.UserPrincipal;
import com.github.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Сервис для работы с пользователями
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    // Получить текущего аутентифицированного пользователя
    @Override
    public User getAuthenticatedUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(principal.getUsername());
    }

    // Найти заказы пользователя
    @Override
    public Page<Order> searchUserOrders(SearchRequest request, Pageable pageable) {
        User user = getAuthenticatedUser();
        return orderRepository.searchUserOrders(user.getId(), request.getSearchType(), request.getText(), pageable);
    }

    // Редактировать информацию о пользователе
    @Override
    @Transactional
    public MessageResponse editUserInfo(EditUserRequest request) {
        User user = getAuthenticatedUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setCity(request.getCity());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPostIndex(request.getPostIndex());
        return new MessageResponse("alert-success", SuccessMessage.USER_UPDATED);
    }

    // Изменить пароль
    @Override
    @Transactional
    public MessageResponse changePassword(ChangePasswordRequest request) {
        if (request.getPassword() != null && !request.getPassword().equals(request.getPassword2())) {
            return new MessageResponse("passwordError", ErrorMessage.PASSWORDS_DO_NOT_MATCH);
        }
        User user = getAuthenticatedUser();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return new MessageResponse("alert-success", SuccessMessage.PASSWORD_CHANGED);
    }
}
