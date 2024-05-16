package com.github.onlineshop.service.impl;

import com.github.onlineshop.constants.ErrorMessage;
import com.github.onlineshop.constants.SuccessMessage;
import com.github.onlineshop.domain.Order;
import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.PerfumeRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.dto.response.UserInfoResponse;
import com.github.onlineshop.repository.OrderRepository;
import com.github.onlineshop.repository.PerfumeRepository;
import com.github.onlineshop.repository.UserRepository;
import com.github.onlineshop.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

// Сервис для управления данными в приложении от имени администратора
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserRepository userRepository;
    private final PerfumeRepository perfumeRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    // Получить список парфюмерии
    @Override
    public Page<Perfume> getPerfumes(Pageable pageable) {
        return perfumeRepository.findAllByOrderByPriceAsc(pageable);
    }

    // Поиск парфюмерии
    @Override
    public Page<Perfume> searchPerfumes(SearchRequest request, Pageable pageable) {
        return perfumeRepository.searchPerfumes(request.getSearchType(), request.getText(), pageable);
    }

    // Получить список пользователей
    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    // Поиск пользователей
    @Override
    public Page<User> searchUsers(SearchRequest request, Pageable pageable) {
        return userRepository.searchUsers(request.getSearchType(), request.getText(), pageable);
    }

    // Получить информацию о заказе
    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.getById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND));
    }

    // Получить список заказов
    @Override
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);

    }

    // Поиск заказов
    @Override
    public Page<Order> searchOrders(SearchRequest request, Pageable pageable) {
        return orderRepository.searchOrders(request.getSearchType(), request.getText(), pageable);
    }

    // Получить парфюм по ID
    @Override
    public Perfume getPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PERFUME_NOT_FOUND));
    }

    // Редактирование парфюма
    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse editPerfume(PerfumeRequest perfumeRequest, MultipartFile file) {
        return savePerfume(perfumeRequest, file, SuccessMessage.PERFUME_EDITED);
    }

    // Добавление парфюма
    @Override
    @SneakyThrows
    @Transactional
    public MessageResponse addPerfume(PerfumeRequest perfumeRequest, MultipartFile file) {
        return savePerfume(perfumeRequest, file, SuccessMessage.PERFUME_ADDED);
    }

    // Получить пользователя по ID
    @Override
    public UserInfoResponse getUserById(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.USER_NOT_FOUND));
        Page<Order> orders = orderRepository.findOrderByUserId(userId, pageable);
        return new UserInfoResponse(user, orders);
    }

    // Сохранение нового или измененного парфюма в БД
    private MessageResponse savePerfume(PerfumeRequest perfumeRequest, MultipartFile file, String message) throws IOException {
        Perfume perfume = modelMapper.map(perfumeRequest, Perfume.class);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            perfume.setFilename(resultFilename);
        }
        perfumeRepository.save(perfume);
        return new MessageResponse("alert-success", message);
    }
}
