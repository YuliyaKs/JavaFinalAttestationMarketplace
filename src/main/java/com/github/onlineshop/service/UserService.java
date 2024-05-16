package com.github.onlineshop.service;

import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.ChangePasswordRequest;
import com.github.onlineshop.dto.request.EditUserRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getAuthenticatedUser();

    Page<Order> searchUserOrders(SearchRequest request, Pageable pageable);

    MessageResponse editUserInfo(EditUserRequest request);

    MessageResponse changePassword(ChangePasswordRequest request);
}
