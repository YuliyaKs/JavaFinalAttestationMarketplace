package com.github.onlineshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Заказы
@Data
@Entity
@Table(name = "orders")
public class Order {

    // Идентификационный номер заказа
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_seq")
    @SequenceGenerator(name = "orders_seq", sequenceName = "orders_seq", initialValue = 6, allocationSize = 1)
    private Long id;

    // Общая сумма заказа
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    // Дата заказа
    @Column(name = "date", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime date = LocalDateTime.now();

    // Имя получателя
    @Column(name = "first_name", nullable = false)
    private String firstName;

    // Фамилия получателя
    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Город получателя
    @Column(name = "city", nullable = false)
    private String city;

    // Адрес получателя
    @Column(name = "address", nullable = false)
    private String address;

    // Электронная почта получателя
    @Column(name = "email", nullable = false)
    private String email;

    // Номер телефона получателя
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    // Почтовый индекс получателя
    @Column(name = "post_index", nullable = false)
    private Integer postIndex;

    // Список заказанной парфюмерии
    @ManyToMany
    private List<Perfume> perfumes = new ArrayList<>();

    // Заказчик
    @ManyToOne
    private User user;
}
