package com.github.onlineshop.service;

import com.github.onlineshop.domain.Perfume;

import java.util.List;

public interface CartService {

    List<Perfume> getPerfumesInCart();

    void addPerfumeToCart(Long perfumeId);

    void removePerfumeFromCart(Long perfumeId);
}
