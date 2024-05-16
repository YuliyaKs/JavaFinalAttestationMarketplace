package com.github.onlineshop.service.impl;

import com.github.onlineshop.constants.ErrorMessage;
import com.github.onlineshop.domain.Perfume;
import com.github.onlineshop.dto.request.SearchRequest;
import com.github.onlineshop.repository.PerfumeRepository;
import com.github.onlineshop.service.PerfumeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

// Сервис для управления парфюмерией
@Service
@RequiredArgsConstructor
public class PerfumeServiceImpl implements PerfumeService {

    private final PerfumeRepository perfumeRepository;
    private final ModelMapper modelMapper;

    // Получить парфюм по ID
    @Override
    public Perfume getPerfumeById(Long perfumeId) {
        return perfumeRepository.findById(perfumeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.PERFUME_NOT_FOUND));
    }

    // Получить популярную парфюмерию
    @Override
    public List<Perfume> getPopularPerfumes() {
        List<Long> perfumeIds = Arrays.asList(26L, 43L, 46L, 106L, 34L, 76L, 82L, 85L, 27L, 39L, 79L, 86L);
        return perfumeRepository.findByIdIn(perfumeIds);
    }

    // Получить отфильтрованный перечень парфюмерии
    @Override
    public Page<Perfume> getPerfumesByFilterParams(SearchRequest request, Pageable pageable) {
        Integer startingPrice = request.getPrice();
        Integer endingPrice = startingPrice + (startingPrice == 0 ? 50000 : 5000);
        return perfumeRepository.getPerfumesByFilterParams(
                request.getPerfumers(),
                request.getGenders(),
                startingPrice,
                endingPrice,
                pageable);
    }

    // Найти парфюмерию
    @Override
    public Page<Perfume> searchPerfumes(SearchRequest request, Pageable pageable) {
        return perfumeRepository.searchPerfumes(request.getSearchType(), request.getText(), pageable);
    }
}
