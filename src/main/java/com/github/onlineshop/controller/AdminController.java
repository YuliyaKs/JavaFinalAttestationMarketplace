package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.dto.request.PerfumeRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.UserInfoResponse;
import com.github.onlineshop.service.AdminService;
import com.github.onlineshop.utils.ControllerUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Контроллер для управления данными в приложении от имени администратора
@Controller
@RequestMapping(PathConstants.ADMIN)
@PreAuthorize("hasAuthority('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    // Создаем счетчик для подсчета добавления парфюмерии для Grafana
    private final Counter addParfumeCounter = Metrics.counter("add_parfume_count");

    private final AdminService adminService;
    private final ControllerUtils controllerUtils;

    // Получить список парфюмерии
    @GetMapping("/perfumes")
    public String getPerfumes(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getPerfumes(pageable));
        return Pages.ADMIN_PERFUMES;
    }

    // Поиск парфюмерии
    @GetMapping("/perfumes/search")
    public String searchPerfumes(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchPerfumes(request, pageable));
        return Pages.ADMIN_PERFUMES;
    }

    // Получить список пользователей
    @GetMapping("/users")
    public String getUsers(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getUsers(pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    // Поиск пользователей
    @GetMapping("/users/search")
    public String searchUsers(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchUsers(request, pageable));
        return Pages.ADMIN_ALL_USERS;
    }

    // Получить информацию о заказе
    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", adminService.getOrder(orderId));
        return Pages.ORDER;
    }

    // Получить список заказов
    @GetMapping("/orders")
    public String getOrders(Pageable pageable, Model model) {
        controllerUtils.addPagination(model, adminService.getOrders(pageable));
        return Pages.ORDERS;
    }

    // Поиск заказов
    @GetMapping("/orders/search")
    public String searchOrders(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, adminService.searchOrders(request, pageable));
        return Pages.ORDERS;
    }

    // Получить парфюм по ID
    @GetMapping("/perfume/{perfumeId}")
    public String getPerfume(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", adminService.getPerfumeById(perfumeId));
        return Pages.ADMIN_EDIT_PERFUME;
    }

    // Редактирование парфюма
    @PostMapping("/edit/perfume")
    public String editPerfume(@Valid PerfumeRequest perfume, BindingResult bindingResult, Model model,
                              @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "perfume", perfume)) {
            return Pages.ADMIN_EDIT_PERFUME;
        }

        return controllerUtils.setAlertFlashMessage(attributes, "/admin/perfumes", adminService.editPerfume(perfume, file));
    }

    // Добавление парфюма
    @GetMapping("/add/perfume")
    public String addPerfume() {
        return Pages.ADMIN_ADD_PERFUME;
    }

    @PostMapping("/add/perfume")
    public String addPerfume(@Valid PerfumeRequest perfume, BindingResult bindingResult, Model model,
                             @RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (controllerUtils.validateInputFields(bindingResult, model, "perfume", perfume)) {
            return Pages.ADMIN_ADD_PERFUME;
        }

        // Подсчет добавления парфюмерии для Grafana
        addParfumeCounter.increment();

        return controllerUtils.setAlertFlashMessage(attributes, "/admin/perfumes", adminService.addPerfume(perfume, file));
    }

    // Получить пользователя по ID
    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable Long userId, Model model, Pageable pageable) {
        UserInfoResponse userResponse = adminService.getUserById(userId, pageable);
        model.addAttribute("user", userResponse.getUser());
        controllerUtils.addPagination(model, userResponse.getOrders());
        return Pages.ADMIN_USER_DETAIL;
    }
}
