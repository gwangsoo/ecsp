package com.example.xyz.service.mapper;

import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.dto.XyzDetailDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.domain.entity.XyzDetail;
import com.example.xyz.repository.XyzRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * Mapper for the entity {@link Xyz} and its DTO {@link XyzDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class XyzDetailMapper implements EntityMapper<XyzDetailDTO, XyzDetail> {
    @Autowired
    XyzRepository xyzRepository;

    @Mapping(target = "xyz", source = "xyz", qualifiedByName = "xyzId")
    abstract public XyzDetailDTO toDto(XyzDetail s);

    @Mapping(target = "xyz", source = "xyz", qualifiedByName = "toEntityXyz")
    abstract public XyzDetail toEntity(XyzDetailDTO s);

    @Named("xyzId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    abstract public XyzDTO toDtoXyzId(Xyz xyz);

    @Named("toEntityXyz")
    public Xyz toEntityXyz(XyzDTO d) {
        if(d == null) return null;
        if(!StringUtils.hasText(d.getId())) return null;

        Xyz e = new Xyz();
        e.setId(d.getId());

        return xyzRepository.findById(d.getId()).orElse(e);
    }
}
