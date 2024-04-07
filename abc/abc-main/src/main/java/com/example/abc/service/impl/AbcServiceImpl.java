package com.example.abc.service.impl;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.domain.entity.Abc;
import com.example.abc.eventuate.AbcTramMessageConfig;
import com.example.abc.eventuate.event.AbcInsertEvent;
import com.example.abc.eventuate.event.AbcUpdateEvent;
import com.example.abc.repository.AbcRepository;
import com.example.abc.service.AbcService;
import com.example.abc.service.mapper.AbcMapper;
import com.example.ecsp.common.util.JsonUtil;
import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.dto.XyzDetailDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.eventuate.XyzTramMessageConfig;
import com.example.xyz.eventuate.command.XyzRegisterCommand;
import io.eventuate.common.json.mapper.JSonMapper;
import io.eventuate.tram.commands.producer.CommandProducer;
import io.eventuate.tram.events.common.DomainEvent;
import io.eventuate.tram.events.publisher.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing {@link com.example.abc.domain.entity.Abc}.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AbcServiceImpl implements AbcService {

    private final AbcRepository abcRepository;

    private final AbcMapper abcMapper;

    private final DomainEventPublisher domainEventPublisher;
    private final CommandProducer commandProducer;

    @Override
    public AbcDTO save(AbcDTO AbcDTO) {
        log.debug("Request to save Abc : {}", AbcDTO);
        Abc abc = abcMapper.toEntity(AbcDTO);
        abc = abcRepository.save(abc);
        AbcDTO result = abcMapper.toDto(abc);

        log.debug("abc = {}", abc);
        log.debug("abc.toJson1 = {}", JsonUtil.toJson(abc));
        log.debug("abc.toJson2 = {}", JSonMapper.toJson(abc));

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new AbcInsertEvent(abc);
        domainEventPublisher.publish(Abc.class.getName(), abc.getId(), Collections.singletonList(domainEvent));

        // 등록시 다른 서비스에 Command를 실행해야 하는 경우
        commandProducer.send(XyzTramMessageConfig.commandChannel,
                new XyzRegisterCommand(XyzDTO.builder()
                        .name("abc")
                        .age(30L)
                        .status(Xyz.XyzStatus.STANDBY)
                        .xyzDetails(Set.of(XyzDetailDTO.builder().attrName("data").attrValue(abc.getData()).build()))
                        .build()),
                AbcTramMessageConfig.replyChannel,
                Collections.emptyMap() // header
        );

        return result;
    }

    @Override
    public AbcDTO update(AbcDTO AbcDTO) {
        log.debug("Request to update Abc : {}", AbcDTO);
        Abc abc = abcMapper.toEntity(AbcDTO);
        abc = abcRepository.save(abc);
        AbcDTO result = abcMapper.toDto(abc);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new AbcUpdateEvent(abc);
        domainEventPublisher.publish(Abc.class.getName(), abc.getId(), Collections.singletonList(domainEvent));

        return result;
    }

    @Override
    public Optional<AbcDTO> partialUpdate(AbcDTO AbcDTO) {
        log.debug("Request to partially update Abc : {}", AbcDTO);

        return abcRepository
            .findById(AbcDTO.getId())
            .map(existingAbc -> {
                abcMapper.partialUpdate(existingAbc, AbcDTO);

                // 도메인 이벤트 저장
                DomainEvent domainEvent = new AbcUpdateEvent(existingAbc);
                domainEventPublisher.publish(Abc.class.getName(), existingAbc.getId(), Collections.singletonList(domainEvent));

                return existingAbc;
            })
            .map(abcRepository::save)
            .map(savedAbc -> {
                return savedAbc;
            })
            .map(abcMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AbcDTO> findAll(Abc.AbcStatus abcStatus, Pageable pageable) {
        log.debug("Request to get all Abcs");

//        Long length1 = abcRepository.getLength();
//        Page<Long> length2 = abcRepository.getLength(pageable);
//        Long length3 = abcRepository.getLength(AbcRepository.Spec.equalChatRoom(chatRoomId));

        return abcRepository
                .findAll(AbcRepository.Spec.instance().equal("status", abcStatus), pageable)
                .map(abcMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbcDTO> findOne(String id) {
        log.debug("Request to get Abc : {}", id);

//        Specification<Abc> spec = AbcRepository.Spec.instance().equal("status", abcStatus)
//                .and(AbcRepository.Spec.instance().equal("id", id));
//        return abcRepository.findOne(spec).map(abcMapper::toDto);

        return abcRepository.findById(id)
                .map(abcMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Abc : {}", id);

        Abc abc = abcRepository.findById(id).orElse(null);

        if(abc == null) return;

//        Specification<Abc> spec = AbcRepository.Spec.instance().equal("chatRoom", "id", chatRoomId)
//                .and(AbcRepository.Spec.instance().equal("id", id));
//        abcRepository.delete(spec);

        abcRepository.deleteById(id);

        // 도메인 이벤트 저장
        DomainEvent domainEvent = new AbcUpdateEvent(abc);
        domainEventPublisher.publish(Abc.class.getName(), abc.getId(), Collections.singletonList(domainEvent));

    }
}
