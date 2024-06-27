package com.kyrylocodefirst.adapters.api.dto.shared;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuditedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "2027-06-23T20:30")
    private LocalDateTime creationDate;

    @Schema(example = "example")
    private String creationUser;

    @Schema(example = "2027-06-23T20:30")
    private LocalDateTime lastUpdateDate;

    @Schema(example = "example")
    private String lastUpdateUser;
}
