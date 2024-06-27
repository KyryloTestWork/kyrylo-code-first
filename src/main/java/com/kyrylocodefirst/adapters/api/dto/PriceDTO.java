package com.kyrylocodefirst.adapters.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.kyrylocodefirst.adapters.api.dto.shared.AuditedDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PriceDTO extends AuditedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "1")
    private Integer brandId;

    @Schema(example = "2024-06-20T18:29")
    private LocalDateTime startDate;

    @Schema(example = "2024-06-23T18:29")
    private LocalDateTime endDate;

    @Schema(example = "null")
    private Integer priceList;

    @Schema(example = "35455")
    private Integer productId;

    @Schema(example = "0")
    private Integer priority;

    @Schema(example = "19.99")
    private BigDecimal price;

    @Schema(example = "EUR")
    private String currency;
}
