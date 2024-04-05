package com.example.abc.service.mapper;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.domain.entity.Abc;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Abc} and its DTO {@link AbcDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AbcMapper extends EntityMapper<AbcDTO, Abc> {}
