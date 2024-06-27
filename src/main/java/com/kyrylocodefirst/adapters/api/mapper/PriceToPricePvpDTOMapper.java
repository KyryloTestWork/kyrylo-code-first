package com.kyrylocodefirst.adapters.api.mapper;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.kyrylocodefirst.adapters.api.dto.PricePvpDTO;
import com.kyrylocodefirst.domain.model.Price;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceToPricePvpDTOMapper {

    @Mapping(target = "applicationDate", source = "applicationDate")
    PricePvpDTO toDto(final Price model, final LocalDateTime applicationDate);
}
