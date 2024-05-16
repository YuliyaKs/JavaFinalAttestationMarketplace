package com.github.onlineshop.dto.request;

import com.github.onlineshop.constants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotBlank;

// Редактирование данных пользователя
@Data
public class EditUserRequest {

    @NotBlank(message = ErrorMessage.EMPTY_FIRST_NAME)
    private String firstName;

    @NotBlank(message = ErrorMessage.EMPTY_LAST_NAME)
    private String lastName;

    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
    private String email;
}
