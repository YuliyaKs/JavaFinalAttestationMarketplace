package com.github.onlineshop.domain;

import org.springframework.security.core.GrantedAuthority;

// Роль, присвоенная пользователю: обычный пользователь или администратор
public enum Role implements GrantedAuthority {
    USER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
