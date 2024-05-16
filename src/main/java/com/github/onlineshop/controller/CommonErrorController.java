package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

// Контроллер для обработки ошибок
@Controller
public class CommonErrorController implements ErrorController {

    // Возврат пользователю страницы ошибки 404 или страницы ошибки 500
    @RequestMapping(PathConstants.ERROR)
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            model.addAttribute("errorMessage", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
            return Pages.ERROR_404;
        }
        return Pages.ERROR_500;
    }

    // Установка пути ошибки
    @Override
    public String getErrorPath() {
        return PathConstants.ERROR;
    }
}
