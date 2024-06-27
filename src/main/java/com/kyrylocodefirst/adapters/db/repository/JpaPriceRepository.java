package com.kyrylocodefirst.adapters.db.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kyrylocodefirst.adapters.db.entity.PriceEntity;

@Repository
public interface JpaPriceRepository extends JpaRepository<PriceEntity, Integer> {

    List<PriceEntity> findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(
        Integer brandId,
        Integer productId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime);
}
