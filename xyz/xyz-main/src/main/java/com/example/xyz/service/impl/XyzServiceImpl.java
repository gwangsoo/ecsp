package com.example.xyz.service.impl;

import com.example.ecsp.common.util.JsonUtil;
import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.eventuate.event.XyzInsertEvent;
import com.example.xyz.eventuate.event.XyzUpdateEvent;
import com.example.xyz.repository.XyzRepository;
import com.example.xyz.service.XyzService;
import com.example.xyz.service.mapper.XyzMapper;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Service Implementation for managing {@link com.example.xyz.domain.entity.Xyz}.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class XyzServiceImpl implements XyzService {

    private final XyzRepository xyzRepository;

    private final XyzMapper xyzMapper;

    private final DomainEventPublisher domainEventPublisher;
    private final CommandProducer commandProducer;

    @Override
    public XyzDTO save(XyzDTO XyzDTO) {
        log.debug("Request to save Xyz : {}", XyzDTO);
        Xyz xyz = xyzMapper.toEntity(XyzDTO);
        xyz = xyzRepository.save(xyz);
        XyzDTO result = xyzMapper.toDto(xyz);

        log.debug("xyz = {}", xyz);
        log.debug("xyz.toJson1 = {}", JsonUtil.toJson(xyz));
        log.debug("xyz.toJson2 = {}", JSonMapper.toJson(xyz));

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new XyzInsertEvent(xyz);
        domainEventPublisher.publish(Xyz.class.getName(), xyz.getId(), Collections.singletonList(domainEvent));

        return result;
    }

    @Override
    public XyzDTO update(XyzDTO XyzDTO) {
        log.debug("Request to update Xyz : {}", XyzDTO);
        Xyz xyz = xyzMapper.toEntity(XyzDTO);
        xyz = xyzRepository.save(xyz);
        XyzDTO result = xyzMapper.toDto(xyz);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new XyzUpdateEvent(xyz);
        domainEventPublisher.publish(Xyz.class.getName(), xyz.getId(), Collections.singletonList(domainEvent));

        return result;
    }

    @Override
    public Optional<XyzDTO> partialUpdate(XyzDTO XyzDTO) {
        log.debug("Request to partially update Xyz : {}", XyzDTO);

        return xyzRepository
            .findById(XyzDTO.getId())
            .map(existingXyz -> {
                xyzMapper.partialUpdate(existingXyz, XyzDTO);

                // 도메인 이벤트 저장
                DomainEvent domainEvent = new XyzUpdateEvent(existingXyz);
                domainEventPublisher.publish(Xyz.class.getName(), existingXyz.getId(), Collections.singletonList(domainEvent));

                return existingXyz;
            })
            .map(xyzRepository::save)
            .map(savedXyz -> {
                return savedXyz;
            })
            .map(xyzMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<XyzDTO> findAll(String attrValue, Xyz.XyzStatus xyzStatus, Pageable pageable) {
        log.debug("Request to get all Xyzs");

        Specification<Xyz> spec = XyzRepository.Spec.instance().equal("status", xyzStatus);

        if(StringUtils.isNotEmpty(attrValue)) {
            spec = spec.and(XyzRepository.Spec.instance().equal("xyzDetails", "attrValue", attrValue));
        }

        return xyzRepository
                .findAll(spec, pageable)
                .map(xyzMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<XyzDTO> findOne(String id) {
        log.debug("Request to get Xyz : {}", id);

//        Specification<Xyz> spec = XyzRepository.Spec.instance().equal("status", xyzStatus)
//                .and(XyzRepository.Spec.instance().equal("id", id));
//        return xyzRepository.findOne(spec).map(xyzMapper::toDto);

        return xyzRepository.findById(id)
                .map(xyzMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Xyz : {}", id);

        Xyz xyz = xyzRepository.findById(id).orElse(null);

        if(xyz == null) return;

//        Specification<Xyz> spec = XyzRepository.Spec.instance().equal("chatRoom", "id", chatRoomId)
//                .and(XyzRepository.Spec.instance().equal("id", id));
//        xyzRepository.delete(spec);

        xyzRepository.deleteById(id);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new XyzUpdateEvent(xyz);
        domainEventPublisher.publish(Xyz.class.getName(), xyz.getId(), Collections.singletonList(domainEvent));

    }
}
