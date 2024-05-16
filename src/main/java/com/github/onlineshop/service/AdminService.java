package com.github.onlineshop.service;

import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.PerfumeRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.dto.response.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Page<Perfume> getPerfumes(Pageable pageable);

    Page<Perfume> searchPerfumes(SearchRequest request, Pageable pageable);

    Page<User> getUsers(Pageable pageable);

    Page<User> searchUsers(SearchRequest request, Pageable pageable);

    Order getOrder(Long orderId);

    Page<Order> getOrders(Pageable pageable);

    Page<Order> searchOrders(SearchRequest request, Pageable pageable);

    Perfume getPerfumeById(Long perfumeId);

    MessageResponse editPerfume(PerfumeRequest perfumeRequest, MultipartFile file);

    MessageResponse addPerfume(PerfumeRequest perfumeRequest, MultipartFile file);

    UserInfoResponse getUserById(Long userId, Pageable pageable);
}
