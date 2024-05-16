package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.service.PerfumeService;
import com.github.onlineshop.utils.ControllerUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// Контроллер для управления парфюмерией
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PERFUME)
public class PerfumeController {

    // Создаем счетчик для подсчета поисковых запросов для Grafana
    private final Counter searchParfumesCounter = Metrics.counter("search_parfumes_count");

    private final PerfumeService perfumeService;
    private final ControllerUtils controllerUtils;

    // Получить парфюм по ID
    @GetMapping("/{perfumeId}")
    public String getPerfumeById(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", perfumeService.getPerfumeById(perfumeId));
        return Pages.PERFUME;
    }

    // Получить отфильтрованный перечень парфюмерии
    @GetMapping
    public String getPerfumesByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.getPerfumesByFilterParams(request, pageable));
        return Pages.PERFUMES;
    }

    // Найти парфюмерию
    @GetMapping("/search")
    public String searchPerfumes(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.searchPerfumes(request, pageable));

        // Подсчет поисковых запросов для Grafana
        searchParfumesCounter.increment();

        return Pages.PERFUMES;
    }
}
