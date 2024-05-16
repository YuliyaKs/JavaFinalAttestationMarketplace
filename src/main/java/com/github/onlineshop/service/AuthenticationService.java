package com.github.onlineshop.service;

import com.github.onlineshop.dto.request.PasswordResetRequest;
import com.github.onlineshop.dto.response.MessageResponse;

public interface AuthenticationService {

    MessageResponse sendPasswordResetCode(String email);

    String getEmailByPasswordResetCode(String code);

    MessageResponse resetPassword(PasswordResetRequest request);
}
