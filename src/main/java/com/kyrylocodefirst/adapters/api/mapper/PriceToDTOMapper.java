package com.kyrylocodefirst.adapters.api.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.kyrylocodefirst.adapters.api.dto.PriceDTO;
import com.kyrylocodefirst.domain.model.Price;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceToDTOMapper {

    PriceDTO toDto(final Price model);

    Price toModel(final PriceDTO dto);

    List<PriceDTO> toDtoList(final List<Price> prices);

    List<Price> toModelList(final List<Price> PricesDTO);
}
