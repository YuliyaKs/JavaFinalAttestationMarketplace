package com.github.onlineshop.security;

import com.github.onlineshop.domain.User;
import com.github.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Аутентификация пользователя
@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Аутентификация пользователя с активным статусом по емейл
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.getActivationCode() != null ) {
            throw new LockedException("Email not activated");
        }
        return UserPrincipal.create(user);
    }
}
