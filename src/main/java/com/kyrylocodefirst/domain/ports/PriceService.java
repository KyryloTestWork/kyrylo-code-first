package com.kyrylocodefirst.domain.ports;

import java.time.LocalDateTime;
import java.util.List;

import com.kyrylocodefirst.domain.model.Price;

public interface PriceService {

    List<Price> getPrices();

    Price getPriceById(Integer id);

    Price savePrice(Price price);

    Price updatePrice(Integer id, Price price);

    void deletePrice(Integer id);

    Price getPvpPrice(Integer brandId, Integer productId, LocalDateTime applicationDate);
}
