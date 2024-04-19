package com.example.bfi.service.mapper;

import com.example.bfi.domain.entity.Evse;
import com.example.bfi.domain.entity.Location;
import com.example.bfi.domain.dto.EvseDTO;
import com.example.bfi.domain.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Evse} and its DTO {@link EvseDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ConnectorMapper.class})
public interface EvseMapper extends EntityMapper<EvseDTO, Evse> {
//    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
//    EvseDTO toDto(Evse s);
//
//    @Named("locationId")
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "id", source = "id")
//    LocationDTO toDtoLocationId(Location location);
}
