package com.github.onlineshop.security;

import com.github.onlineshop.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Данные о пользователе в целях безопасности
@Data
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String password;
    private final String email;
    private final boolean active;
    private final Collection<? extends GrantedAuthority> authorities;

    // Роль пользователя, список прав пользователя
    public static UserPrincipal create(User user) {
        String userRole = user.getRoles().iterator().next().toString();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(userRole));
        return new UserPrincipal(user.getId(), user.getPassword(), user.getEmail(), user.isActive(), authorities);
    }

    // Учетная запись пользователя не истекла
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Учетная запись не заблокирована
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Учетные данные не истекли
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Пользователь активен
    @Override
    public boolean isEnabled() {
        return active;
    }

    // Получить права пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Получить электронную почту пользователя
    @Override
    public String getUsername() {
        return email;
    }
}
