package com.example.bfi.service.mapper;

import com.example.bfi.domain.dto.TariffDTO;
import com.example.bfi.domain.entity.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Tariff} and its DTO {@link TariffDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffMapper extends EntityMapper<TariffDTO, Tariff> {}
