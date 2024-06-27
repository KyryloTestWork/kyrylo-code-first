package com.kyrylocodefirst.adapters.db.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.kyrylocodefirst.adapters.db.entity.PriceEntity;
import com.kyrylocodefirst.domain.model.Price;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceEntityToModelMapper {

    Price toModel(final PriceEntity priceEntity);

    PriceEntity toEntity(final Price price);

    List<Price> toModelList(final List<PriceEntity> pricesEntities);

    List<PriceEntity> toEntityList(final List<Price> pricesEntities);
}
