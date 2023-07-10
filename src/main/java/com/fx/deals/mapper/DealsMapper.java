package com.fx.deals.mapper;

import com.fx.deals.dto.DealDTO;
import com.fx.deals.model.FXDeal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DealsMapper {
    FXDeal DtoToDeal(DealDTO dealDTO);
    DealDTO dealToDTO(FXDeal fxDeal);
}
