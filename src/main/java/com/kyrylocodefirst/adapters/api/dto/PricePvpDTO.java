package com.kyrylocodefirst.adapters.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class PricePvpDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer productId;

    private Integer brandId;

    private Integer priceList;

    private LocalDateTime applicationDate;

    private BigDecimal price;

    private String currency;
}
