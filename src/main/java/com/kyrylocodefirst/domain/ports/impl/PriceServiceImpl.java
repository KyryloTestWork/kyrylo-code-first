package com.kyrylocodefirst.domain.ports.impl;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.springframework.stereotype.Service;

import com.kyrylocodefirst.adapters.db.entity.PriceEntity;
import com.kyrylocodefirst.adapters.db.mapper.PriceEntityToModelMapper;
import com.kyrylocodefirst.adapters.db.repository.JpaPriceRepository;
import com.kyrylocodefirst.domain.model.Price;
import com.kyrylocodefirst.domain.ports.PriceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final JpaPriceRepository repository;
    private final PriceEntityToModelMapper mapper;

    @Override
    public List<Price> getPrices() {
        return mapper.toModelList(repository.findAll());
    }

    @Override
    public Price getPriceById(Integer id) {
        return repository.findById(id).map(mapper::toModel)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Price with this id: %s not found", id)));
    }

    @Override
    public Price savePrice(Price price) {
        return mapper.toModel(repository.save(mapper.toEntity(price)));
    }

    @Override
    public Price updatePrice(Integer id, Price price) {
        if (price.getPriceList() != null && !id.equals(price.getPriceList())) {
            throw new ValidationException(String.format("PriceList must be null or equal to Id: %s", id));
        }
        getPriceById(id);

        return savePrice(price);
    }

    @Override
    public void deletePrice(Integer id) {
        getPriceById(id);

        repository.deleteById(id);
    }

    @Override
    public Price getPvpPrice(Integer brandId, Integer productId, LocalDateTime applicationDate) {
        return mapper.toModel(
            repository.findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(brandId, productId, applicationDate, applicationDate).stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Pvp price for these brandId: %s and productId: %s and applicationDate: "
                    + "%s not found", brandId, productId, applicationDate))));
    }
}
