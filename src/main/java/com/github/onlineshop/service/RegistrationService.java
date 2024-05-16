package com.github.onlineshop.service;

import com.github.onlineshop.dto.response.MessageResponse;
import com.github.onlineshop.dto.request.UserRequest;

public interface RegistrationService {

    MessageResponse registration(String captchaResponse, UserRequest user);

    MessageResponse activateEmailCode(String code);
}
