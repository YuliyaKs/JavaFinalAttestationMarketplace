package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.dto.request.UserRequest;
import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.service.RegistrationService;
import com.github.onlineshop.utils.ControllerUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

// Контроллер для регистрации пользователей
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.REGISTRATION)
public class RegistrationController {

    // Создаем счетчик для подсчета новых зарегистрированных и активированных пользователей для Grafana
    private final Counter registrationCounter = Metrics.counter("registration_count");

    private final RegistrationService registrationService;
    private final ControllerUtils controllerUtils;

    // Отображение формы регистрации
    @GetMapping
    public String registration() {
        return Pages.REGISTRATION;
    }

    // Обработка запроса на регистрацию пользователя
    @PostMapping
    public String registration(@RequestParam("g-recaptcha-response") String captchaResponse,
                               @Valid UserRequest user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "user", user)) {
            return Pages.REGISTRATION;
        }

        // Проверка на пустое значение капчи (до развертывания на хостинге, когда ключа капчи нет)
        if (captchaResponse == null || captchaResponse.isEmpty()) {
            // Пропускаем проверку капчи и продолжаем регистрацию
            MessageResponse messageResponse = registrationService.registration(null, user);
            if (controllerUtils.validateInputField(model, messageResponse, "user", user)) {
                return Pages.REGISTRATION;
            }
            return controllerUtils.setAlertFlashMessage(redirectAttributes, PathConstants.LOGIN, messageResponse);
        }

        // Использование капчи после развертывания на хостинге и получения ключей капчи
        MessageResponse messageResponse = registrationService.registration(captchaResponse, user);
        if (controllerUtils.validateInputField(model, messageResponse, "user", user)) {
            return Pages.REGISTRATION;
        }
        return controllerUtils.setAlertFlashMessage(redirectAttributes, PathConstants.LOGIN, messageResponse);
    }

    // Активация учетной записи по коду активации
    @GetMapping("/activate/{code}")
    public String activateEmailCode(@PathVariable String code, Model model) {

        // Подсчет новых зарегистрированных и активированных пользователей для Grafana
        registrationCounter.increment();

        return controllerUtils.setAlertMessage(model, Pages.LOGIN, registrationService.activateEmailCode(code));
    }
}
