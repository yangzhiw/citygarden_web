package com.citygarden.web.rest.mapper;

import com.citygarden.domain.*;
import com.citygarden.web.rest.dto.ProvideMerchantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProvideMerchant and its DTO ProvideMerchantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface  ProvideMerchantMapper {

    ProvideMerchantDTO provideMerchantToProvideMerchantDTO(ProvideMerchant provideMerchant);

    ProvideMerchant provideMerchantDTOToProvideMerchant(ProvideMerchantDTO provideMerchantDTO);
}
