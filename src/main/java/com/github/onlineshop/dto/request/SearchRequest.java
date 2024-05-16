package com.github.onlineshop.dto.request;

import lombok.Data;

import java.util.List;

// Поиск
@Data
public class SearchRequest {
    private List<String> perfumers;
    private List<String> genders;
    private Integer price = 0;
    private String searchType;
    private String text;
}
