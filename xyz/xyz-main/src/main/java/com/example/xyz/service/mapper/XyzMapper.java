package com.example.xyz.service.mapper;

import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.entity.Xyz;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Xyz} and its DTO {@link XyzDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {XyzDetailMapper.class})
public interface XyzMapper extends EntityMapper<XyzDTO, Xyz> {

    @AfterMapping
    default void relation(@MappingTarget Xyz e) {
        // parent-child 관계 설정
        if(e.getXyzDetails() != null) {
            e.getXyzDetails().forEach(x -> x.setXyz(e));
        }
    }
}
