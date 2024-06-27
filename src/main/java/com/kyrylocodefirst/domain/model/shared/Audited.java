package com.kyrylocodefirst.domain.model.shared;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Audited implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime creationDate;

    private String creationUser;

    private LocalDateTime lastUpdateDate;

    private String lastUpdateUser;
}
