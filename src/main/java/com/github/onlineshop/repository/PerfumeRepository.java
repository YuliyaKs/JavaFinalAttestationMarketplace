package com.github.onlineshop.repository;

import com.github.onlineshop.domain.Perfume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Репозиторий парфюмерии
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    // Парфюм по ID
    List<Perfume> findByIdIn(List<Long> perfumesIds);

    // Парфюм по заказу и цене
    Page<Perfume> findAllByOrderByPriceAsc(Pageable pageable);

    // Поиск парфюмерии
    @Query("SELECT perfume FROM Perfume perfume WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'perfumeTitle' THEN UPPER(perfume.perfumeTitle) " +
            "   WHEN :searchType = 'country' THEN UPPER(perfume.country) " +
            "   ELSE UPPER(perfume.perfumer) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price ASC")
    Page<Perfume> searchPerfumes(String searchType, String text, Pageable pageable);

    // Поиск парфюмерии по фильтрам
    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.perfumeGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY perfume.price ASC")
    Page<Perfume> getPerfumesByFilterParams(
            List<String> perfumers,
            List<String> genders,
            Integer priceStart,
            Integer priceEnd,
            Pageable pageable);
}
