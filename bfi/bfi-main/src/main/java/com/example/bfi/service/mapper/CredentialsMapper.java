package com.example.bfi.service.mapper;

import com.example.bfi.domain.dto.CredentialsDTO;
import com.example.bfi.domain.entity.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Credentials} and its DTO {@link CredentialsDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CredentialsMapper extends EntityMapper<CredentialsDTO, Credentials> {}
