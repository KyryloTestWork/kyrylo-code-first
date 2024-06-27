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

    @Schema(example = "35455")
    private Integer productId;

    @Schema(example = "1")
    private Integer brandId;

    @Schema(example = "1")
    private Integer priceList;

    @Schema(example = "2020-06-14T11:00")
    private LocalDateTime applicationDate;

    @Schema(example = "35.5")
    private BigDecimal price;

    @Schema(example = "EUR")
    private String currency;
}
