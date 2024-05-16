package com.github.onlineshop.service.impl;

import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.repository.PerfumeRepository;
import com.github.onlineshop.service.CartService;
import com.github.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Сервис для управления корзиной пользователя
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final PerfumeRepository perfumeRepository;

    // Получить парфюмерию, которая в корзине
    @Override
    public List<Perfume> getPerfumesInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    // Добавить парфюм в корзину
    @Override
    @Transactional
    public void addPerfumeToCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().add(perfume);
    }

    // Удалить парфюм из корзины
    @Override
    @Transactional
    public void removePerfumeFromCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        Perfume perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().remove(perfume);
    }
}
