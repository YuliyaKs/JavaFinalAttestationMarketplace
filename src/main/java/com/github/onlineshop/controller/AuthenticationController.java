package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.dto.request.PasswordResetRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.service.AuthenticationService;
import com.github.onlineshop.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Контроллер для аутентификации пользователей
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.AUTH)
public class AuthenticationController {

    private final AuthenticationService authService;
    private final ControllerUtils controllerUtils;

    // Отобразить форму сброса пароля
    @GetMapping("/forgot")
    public String forgotPassword() {
        return Pages.FORGOT_PASSWORD;
    }

    // Отправить код сброса пароля на емейл
    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email, Model model) {
        return controllerUtils.setAlertMessage(model, Pages.FORGOT_PASSWORD, authService.sendPasswordResetCode(email));
    }

    // Получить емейл пользователя по коду сброса пароля
    @GetMapping("/reset/{code}")
    public String resetPassword(@PathVariable String code, Model model) {
        model.addAttribute("email", authService.getEmailByPasswordResetCode(code));
        return Pages.RESET_PASSWORD;
    }

    // Направить пользователя на страницу входа по новому паролю
    @PostMapping("/reset")
    public String resetPassword(@Valid PasswordResetRequest request, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes, Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "email", request.getEmail())) {
            return Pages.RESET_PASSWORD;
        }
        MessageResponse messageResponse = authService.resetPassword(request);
        if (controllerUtils.validateInputField(model, messageResponse, "email", request.getEmail())) {
            return Pages.RESET_PASSWORD;
        }
        return controllerUtils.setAlertFlashMessage(redirectAttributes, PathConstants.LOGIN, messageResponse);
    }
}
