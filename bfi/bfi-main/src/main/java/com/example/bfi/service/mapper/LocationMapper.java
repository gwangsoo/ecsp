package com.example.bfi.service.mapper;

import com.example.bfi.domain.entity.Location;
import com.example.bfi.domain.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EvseMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {}
