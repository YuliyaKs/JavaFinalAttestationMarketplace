package com.github.onlineshop.controller;

import com.github.onlineshop.constants.Pages;
import com.github.onlineshop.constants.PathConstants;
import com.github.onlineshop.domain.User;
import com.github.onlineshop.dto.request.OrderRequest;
import com.github.onlineshop.service.OrderService;
import com.github.onlineshop.service.UserService;
import com.github.onlineshop.utils.ControllerUtils;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

// Контроллер для управления заказами
@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.ORDER)
public class OrderController {

    // Создаем счетчик для подсчета созданных заказов для Grafana
    private final Counter createOrderCounter = Metrics.counter("create_order_count");

    private final OrderService orderService;
    private final UserService userService;
    private final ControllerUtils controllerUtils;

    // Получить заказ по ID
    @GetMapping("/{orderId}")
    public String getOrder(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrder(orderId));
        return Pages.ORDER;
    }

    // Получить корзину пользователя
    @GetMapping
    public String getOrdering(Model model) {
        model.addAttribute("perfumes", orderService.getOrdering());
        return Pages.ORDERING;
    }

    // Получить список заказов пользователя
    @GetMapping("/user/orders")
    public String getUserOrdersList(Model model, Pageable pageable) {
        controllerUtils.addPagination(model, orderService.getUserOrdersList(pageable));
        return Pages.ORDERS;
    }

    // Создать новый заказ
    @PostMapping
    public String postOrder(@Valid OrderRequest orderRequest, BindingResult bindingResult, Model model) {
        User user = userService.getAuthenticatedUser();
        if (controllerUtils.validateInputFields(bindingResult, model, "perfumes", user.getPerfumeList())) {
            return Pages.ORDERING;
        }
        model.addAttribute("orderId", orderService.postOrder(user, orderRequest));

        // Подсчет созданных заказов для Grafana
        createOrderCounter.increment();

        return Pages.ORDER_FINALIZE;
    }
}
