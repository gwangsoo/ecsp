package com.example.xyz.service.mapper;

import com.example.ecsp.common.jpa.AbstractAuditingDTO;
import com.example.ecsp.common.util.TsidUtil;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper<D, E> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(List<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);

    @BeforeMapping
    default void generateId(D d) {
        if(d == null) return;

        if(d instanceof AbstractAuditingDTO) {
            AbstractAuditingDTO abstractAuditingDTO = (AbstractAuditingDTO)d;

            if(abstractAuditingDTO.getId() == null || !StringUtils.hasText(abstractAuditingDTO.getId().toString())) {
                abstractAuditingDTO.setId(TsidUtil.getId());
            }
        }
    }
}
