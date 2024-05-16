package com.github.onlineshop.domain;

import lombok.*;

import javax.persistence.*;

// Парфюмерия
@Entity
@Table(name = "perfumes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Perfume {

    // ID парфюма
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfume_id_seq")
    @SequenceGenerator(name = "perfume_id_seq", sequenceName = "perfume_id_seq", initialValue = 109, allocationSize = 1)
    private Long id;

    // Название парфюма
    @Column(name = "perfume_title", nullable = false)
    private String perfumeTitle;

    // Название бренда
    @Column(name = "perfumer", nullable = false)
    private String perfumer;

    // Год создания парфюма
    @Column(name = "year", nullable = false)
    private Integer year;

    // Страна-производитель
    @Column(name = "country", nullable = false)
    private String country;

    // Пол, для кого предназначен парфюм
    @Column(name = "perfume_gender", nullable = false)
    private String perfumeGender;

    // Верхние ноты аромата
    @Column(name = "fragrance_top_notes", nullable = false)
    private String fragranceTopNotes;

    // Средние ноты аромата
    @Column(name = "fragrance_middle_notes", nullable = false)
    private String fragranceMiddleNotes;

    // Базовые ноты аромата
    @Column(name = "fragrance_base_notes", nullable = false)
    private String fragranceBaseNotes;

    // Описание
    @Column(name = "description")
    private String description;

    // Файл с изображением парфюма
    @Column(name = "filename")
    private String filename;

    // Цена парфюма
    @Column(name = "price", nullable = false)
    private Integer price;

    // Объем парфюма в мл
    @Column(name = "volume", nullable = false)
    private String volume;

    // Тип парфюма: туалетная или парфюмерная вода
    @Column(name = "type", nullable = false)
    private String type;
}
