package com.kyrylocodefirst.adapters.api;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.kyrylocodefirst.adapters.api.dto.PriceDTO;
import com.kyrylocodefirst.adapters.api.dto.PricePvpDTO;
import com.kyrylocodefirst.adapters.api.mapper.PriceToDTOMapper;
import com.kyrylocodefirst.adapters.api.mapper.PriceToPricePvpDTOMapper;
import com.kyrylocodefirst.domain.model.Price;
import com.kyrylocodefirst.domain.ports.PriceService;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @InjectMocks
    private PriceController priceController;
    @Mock
    private PriceService service;
    @Mock
    private PriceToDTOMapper priceMapper;
    @Mock
    private PriceToPricePvpDTOMapper priceToPricePvpDTOMapper;

    @Test
    void getPrices() {
        final var priceList = List.of(buildPrice());
        final var priceDtoList = List.of(buildPriceDTO());

        given(service.getPrices()).willReturn(priceList);
        given(priceMapper.toDtoList(priceList)).willReturn(priceDtoList);

        final var response = priceController.getPrices();

        then(priceMapper).should(times(1)).toDtoList(priceList);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
    }

    @Test
    void createPrice() {
        final var price = buildPrice();
        final var priceDto = buildPriceDTO();

        given(priceMapper.toModel(priceDto)).willReturn(price);
        given(service.savePrice(price)).willReturn(price);
        given(priceMapper.toDto(price)).willReturn(priceDto);

        final var response = priceController.createPrice(priceDto);

        then(priceMapper).should(times(1)).toModel(priceDto);
        then(priceMapper).should(times(1)).toDto(price);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getPrice() {
        final var price = buildPrice();
        final var priceDto = buildPriceDTO();

        given(service.getPriceById(INTEGER_ONE)).willReturn(price);
        given(priceMapper.toDto(price)).willReturn(priceDto);

        final var response = priceController.getPrice(INTEGER_ONE);

        then(priceMapper).should(times(1)).toDto(price);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void updatePrice() {
        final var price = buildPrice();
        final var priceDto = buildPriceDTO();

        given(priceMapper.toModel(priceDto)).willReturn(price);
        given(service.updatePrice(INTEGER_ONE, price)).willReturn(price);
        given(priceMapper.toDto(price)).willReturn(priceDto);

        final var response = priceController.updatePrice(INTEGER_ONE, priceDto);

        then(priceMapper).should(times(1)).toModel(priceDto);
        then(priceMapper).should(times(1)).toDto(price);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deletePrice() {
        final var response = priceController.deletePrice(INTEGER_ONE);

        then(service).should(times(1)).deletePrice(INTEGER_ONE);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getPvpPrice() {
        final var price = buildPrice();
        final var pricePvpDTO = new PricePvpDTO();

        given(service.getPvpPrice(INTEGER_ONE, INTEGER_ONE, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))).willReturn(price);
        given(priceToPricePvpDTOMapper.toDto(price, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))).willReturn(pricePvpDTO);

        final var response = priceController.getPvpPrice(INTEGER_ONE, INTEGER_ONE, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));

        then(priceToPricePvpDTOMapper).should(times(1)).toDto(price, LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
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

    private static PriceDTO buildPriceDTO() {
        return PriceDTO.builder()
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
