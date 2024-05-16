package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Контроллер для управления корзиной пользователя
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.CART)
public class CartController {

    private final CartService cartService;

    // Получить корзину пользователя
    @GetMapping
    public String getCart(Model model) {
        model.addAttribute("perfumes", cartService.getPerfumesInCart());
        return Pages.CART;
    }

    // Добавить парфюм в корзину
    @PostMapping("/add")
    public String addPerfumeToCart(@RequestParam("perfumeId") Long perfumeId) {
        cartService.addPerfumeToCart(perfumeId);
        return "redirect:" + PathConstants.CART;
    }

    // Удалить парфюм из корзины
    @PostMapping("/remove")
    public String removePerfumeFromCart(@RequestParam("perfumeId") Long perfumeId) {
        cartService.removePerfumeFromCart(perfumeId);
        return "redirect:" + PathConstants.CART;
    }
}
