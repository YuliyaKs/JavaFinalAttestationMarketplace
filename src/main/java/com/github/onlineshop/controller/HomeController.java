package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Контроллер для главной страницы
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PerfumeService perfumeService;

    // Главная страница
    @GetMapping
    public String home(Model model) {
        model.addAttribute("perfumes", perfumeService.getPopularPerfumes());
        return Pages.HOME;
    }
}
