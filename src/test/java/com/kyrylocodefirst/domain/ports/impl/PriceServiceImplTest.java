package com.kyrylocodefirst.domain.ports.impl;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kyrylocodefirst.adapters.db.entity.PriceEntity;
import com.kyrylocodefirst.adapters.db.mapper.PriceEntityToModelMapper;
import com.kyrylocodefirst.adapters.db.repository.JpaPriceRepository;
import com.kyrylocodefirst.domain.model.Price;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    private Price price;
    private PriceEntity priceEntity;
    private List<PriceEntity> priceEntityList;
    private List<Price> priceList;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private JpaPriceRepository repository;
    @Mock
    private PriceEntityToModelMapper mapper;

    @BeforeEach
    void setUp() {
        price = buildPrice();
        priceEntity = buildPriceEntity();
        priceEntityList = List.of(priceEntity);
        priceList = List.of(price);
    }

    @Test
    void getPrices() {
        given(repository.findAll()).willReturn(priceEntityList);
        given(mapper.toModelList(priceEntityList)).willReturn(priceList);

        final var expectedList = priceService.getPrices();

        then(mapper).should(times(1)).toModelList(priceEntityList);
        assertEquals(expectedList, priceList);
    }

    @Test
    void getPriceById() {
        given(repository.findById(INTEGER_ONE)).willReturn(Optional.ofNullable(priceEntity));
        given(mapper.toModel(priceEntity)).willReturn(price);

        final var expectedPrice = priceService.getPriceById(INTEGER_ONE);

        then(mapper).should(times(1)).toModel(priceEntity);
        assertEquals(expectedPrice, price);
    }

    @Test
    void getPriceById_shouldThrowException() {
        final var exception = new EntityNotFoundException(String.format("Price with this id: %s not found", 1));

        given(repository.findById(INTEGER_ONE)).willThrow(exception);

        final var error = assertThrows(EntityNotFoundException.class, () -> priceService.getPriceById(INTEGER_ONE));

        assertThrows(EntityNotFoundException.class, () -> priceService.getPriceById(INTEGER_ONE));

        then(mapper).should(never()).toModel(priceEntity);

        assertEquals("Price with this id: 1 not found", error.getMessage());
    }

    @Test
    void savePrice() {
        given(mapper.toEntity(price)).willReturn(priceEntity);
        given(repository.save(priceEntity)).willReturn(priceEntity);
        given(mapper.toModel(priceEntity)).willReturn(price);

        final var expectedPrice = priceService.savePrice(price);

        then(mapper).should(times(1)).toModel(priceEntity);
        assertEquals(expectedPrice, price);
    }

    @Test
    void updatePrice() {
        given(mapper.toEntity(price)).willReturn(priceEntity);
        given(repository.save(priceEntity)).willReturn(priceEntity);
        given(mapper.toModel(priceEntity)).willReturn(price);
        given(repository.findById(INTEGER_ONE)).willReturn(Optional.ofNullable(priceEntity));

        final var expectedPrice = priceService.updatePrice(INTEGER_ONE, price);

        then(mapper).should(times(2)).toModel(priceEntity);
        assertEquals(expectedPrice, price);
    }

    @Test
    void updatePrice_shouldUpdatePriceListNull() {
        price.setPriceList(null);
        priceEntity.setPriceList(null);
        given(mapper.toEntity(price)).willReturn(priceEntity);
        given(repository.save(priceEntity)).willReturn(priceEntity);
        given(mapper.toModel(priceEntity)).willReturn(price);
        given(repository.findById(INTEGER_ONE)).willReturn(Optional.ofNullable(priceEntity));

        final var expectedPrice = priceService.updatePrice(INTEGER_ONE, price);

        then(mapper).should(times(2)).toModel(priceEntity);
        assertEquals(expectedPrice, price);
    }

    @Test
    void updatePrice_shouldThrowException_IdAndPriceList_notEquals() {

        final var expectationError = assertThrows(ValidationException.class, () -> priceService.updatePrice(INTEGER_TWO, price));

        then(mapper).should(never()).toModel(priceEntity);
        then(repository).should(never()).findById(INTEGER_ONE);
        then(repository).should(never()).save(priceEntity);
        assertEquals(expectationError.getMessage(), "PriceList must be null or equal to Id: 2");
    }

    @Test
    void deletePrice() {
        given(repository.findById(INTEGER_ONE)).willReturn(Optional.ofNullable(priceEntity));
        given(mapper.toModel(priceEntity)).willReturn(price);

        assertDoesNotThrow(() -> priceService.deletePrice(INTEGER_ONE));
    }

    @Test
    void getPvpPrice() {
        given(repository.findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(INTEGER_ONE, INTEGER_ONE,
            LocalDateTime.now().truncatedTo(ChronoUnit.DAYS), LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))).willReturn(priceEntityList);
        given(mapper.toModel(priceEntity)).willReturn(price);

        final var expectedPrice = priceService.getPvpPrice(INTEGER_ONE, INTEGER_ONE, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));

        then(mapper).should(times(1)).toModel(priceEntity);
        assertEquals(expectedPrice, price);
    }

    @Test
    void getPvpPrice_shouldThrowException() {
        final var exception =
            new EntityNotFoundException(String.format("Pvp price for these brandId: %s and productId: %s and applicationDate: %s not found",
                1, 1, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)));

        given(repository.findAllByBrandIdAndProductIdAndStartDateBeforeAndEndDateAfter(INTEGER_ONE, INTEGER_ONE,
            LocalDateTime.now().truncatedTo(ChronoUnit.DAYS), LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))).willThrow(exception);

        final var expectedPrice = assertThrows(EntityNotFoundException.class,
            () -> priceService.getPvpPrice(INTEGER_ONE, INTEGER_ONE, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)));

        then(mapper).should(never()).toModel(priceEntity);
        assertTrue(expectedPrice.getMessage().contains("Pvp price for these brandId: 1 and productId: 1"));
    }

    private static Price buildPrice() {
        return Price.builder()
            .priceList(1)
            .brandId(1)
            .productId(35455)
            .price(new BigDecimal("35.50"))
            .priority(0)
            .startDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            .endDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            .creationUser("kyrylo")
            .build();
    }

    private static PriceEntity buildPriceEntity() {
        return PriceEntity.builder()
            .priceList(1)
            .brandId(1)
            .productId(35455)
            .price(new BigDecimal("35.50"))
            .priority(0)
            .startDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            .endDate(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
            .creationUser("kyrylo")
            .build();
    }
}
