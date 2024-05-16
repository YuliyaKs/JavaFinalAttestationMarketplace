package com.github.onlineshop.repository;

import com.github.onlineshop.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// Репозиторий пользователей
public interface UserRepository extends JpaRepository<User, Long> {

    // Найти всех пользователей
    @EntityGraph(attributePaths = "roles")
    Page<User> findAll(Pageable pageable);

    // Найти по электронной почте
    @EntityGraph(attributePaths = "roles")
    User findByEmail(String email);

    // Найти по коду активации
    @EntityGraph(attributePaths = "roles")
    User findByActivationCode(String code);

    // Получить емейл по коду сброса пароля
    @Query("SELECT user.email FROM User user WHERE user.passwordResetCode = :code")
    Optional<String> getEmailByPasswordResetCode(String code);

    // Найти пользователей
    @EntityGraph(attributePaths = "roles")
    @Query("SELECT user FROM User user WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'firstName' THEN UPPER(user.firstName) " +
            "   WHEN :searchType = 'lastName' THEN UPPER(user.lastName) " +
            "   ELSE UPPER(user.email) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%'))")
    Page<User> searchUsers(String searchType, String text, Pageable pageable);
}
