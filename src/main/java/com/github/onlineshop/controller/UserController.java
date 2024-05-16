package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.dto.request.ChangePasswordRequest;
import com.github.onlineshop.dto.request.EditUserRequest;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.service.UserService;
import com.github.onlineshop.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Контроллер для работы с пользователями
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.USER)
public class UserController {

    private final UserService userService;
    private final ControllerUtils controllerUtils;

    // Отобразить страницу контакты маркетплейса
    @GetMapping("/contacts")
    public String contacts() {
        return Pages.CONTACTS;
    }

    // Отобразить страницу сброса пароля
    @GetMapping("/reset")
    public String passwordReset() {
        return Pages.USER_PASSWORD_RESET;
    }

    // Отобразить учетную запись пользователя
    @GetMapping("/account")
    public String userAccount(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return Pages.USER_ACCOUNT;
    }

    // Найти заказы пользователя
    @GetMapping("/orders/search")
    public String searchUserOrders(SearchRequest request, Pageable pageable, Model model) {
        controllerUtils.addPagination(request, model, userService.searchUserOrders(request, pageable));
        return Pages.ORDERS;
    }

    // Отобразить информацию о пользователе
    @GetMapping("/info")
    public String userInfo(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return Pages.USER_INFO;
    }

    // Отобразить страницу для редактирования данных пользователя
    @GetMapping("/info/edit")
    public String editUserInfo(Model model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return Pages.USER_INFO_EDIT;
    }

    // Редактировать данные пользователя
    @PostMapping("/info/edit")
    public String editUserInfo(@Valid EditUserRequest request, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes, Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "user", request)) {
            return Pages.USER_INFO_EDIT;
        }
        MessageResponse messageResponse = userService.editUserInfo(request);
        return controllerUtils.setAlertFlashMessage(redirectAttributes, "/user/info", messageResponse);
    }

    // Изменить пароль пользователя
    @PostMapping("/change/password")
    public String changePassword(@Valid ChangePasswordRequest request, BindingResult bindingResult, Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "request", request)) {
            return Pages.USER_PASSWORD_RESET;
        }
        MessageResponse messageResponse = userService.changePassword(request);
        if (controllerUtils.validateInputField(model, messageResponse, "request", request)) {
            return Pages.USER_PASSWORD_RESET;
        }
        return controllerUtils.setAlertMessage(model, Pages.USER_PASSWORD_RESET, messageResponse);
    }
}
