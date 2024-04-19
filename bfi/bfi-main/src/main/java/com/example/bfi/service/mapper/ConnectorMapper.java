package com.example.bfi.service.mapper;

import com.example.bfi.domain.entity.Connector;
import com.example.bfi.domain.entity.Evse;
import com.example.bfi.domain.dto.ConnectorDTO;
import com.example.bfi.domain.dto.EvseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Connector} and its DTO {@link ConnectorDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConnectorMapper extends EntityMapper<ConnectorDTO, Connector> {
//    @Mapping(target = "evse", source = "evse", qualifiedByName = "evseId")
//    ConnectorDTO toDto(Connector s);
//
//    @Named("evseId")
//    @BeanMapping(ignoreByDefault = true)
//    @Mapping(target = "id", source = "id")
//    EvseDTO toDtoEvseId(Evse evse);
}
