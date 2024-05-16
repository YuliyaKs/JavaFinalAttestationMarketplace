package com.github.onlineshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

// Сообщение
@Data
@AllArgsConstructor
public class MessageResponse {
    private String response;
    private String message;
}
