package com.example.bfi.service.mapper;

import com.example.bfi.domain.dto.VersionDTO;
import com.example.bfi.domain.entity.Version;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link com.example.bfi.domain.entity.Version} and its DTO {@link com.example.bfi.domain.dto.VersionDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VersionMapper extends EntityMapper<VersionDTO, Version> {}
