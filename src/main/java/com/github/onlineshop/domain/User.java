package com.github.onlineshop.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

// Пользователи
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {

    // ID пользователя
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", initialValue = 4, allocationSize = 1)
    private Long id;

    // Электронная почта пользователя
    @Column(name = "email", nullable = false)
    private String email;

    // Пароль пользователя
    @Column(name = "password", nullable = false)
    private String password;

    // Имя пользователя
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Фамилия пользователя
    @Column(name = "last_name")
    private String lastName;

    // Город пользователя
    @Column(name = "city")
    private String city;

    // Адрес
    @Column(name = "address")
    private String address;

    // Номер телефона
    @Column(name = "phone_number")
    private String phoneNumber;

    // Почтовый индекс
    @Column(name = "post_index")
    private String postIndex;

    // Код активации
    @Column(name = "activation_code")
    private String activationCode;

    // Код сброса пароля
    @Column(name = "password_reset_code")
    private String passwordResetCode;

    // Статус
    @Column(name = "active")
    private boolean active;

    // Роли пользователя
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    // Список парфюмерии в корзине
    @ManyToMany
    private List<Perfume> perfumeList;
}
