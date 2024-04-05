package com.example.abc.service.impl;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.domain.entity.Abc;
import com.example.abc.repository.AbcRepository;
import com.example.abc.service.AbcService;
import com.example.abc.service.mapper.AbcMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Override
    public AbcDTO save(AbcDTO AbcDTO) {
        log.debug("Request to save Abc : {}", AbcDTO);
        Abc abc = abcMapper.toEntity(AbcDTO);
        abc = abcRepository.save(abc);
        AbcDTO result = abcMapper.toDto(abc);
        return result;
    }

    @Override
    public AbcDTO update(AbcDTO AbcDTO) {
        log.debug("Request to update Abc : {}", AbcDTO);
        Abc abc = abcMapper.toEntity(AbcDTO);
        abc = abcRepository.save(abc);
        AbcDTO result = abcMapper.toDto(abc);
        return result;
    }

    @Override
    public Optional<AbcDTO> partialUpdate(AbcDTO AbcDTO) {
        log.debug("Request to partially update Abc : {}", AbcDTO);

        return abcRepository
            .findById(AbcDTO.getId())
            .map(existingAbc -> {
                abcMapper.partialUpdate(existingAbc, AbcDTO);
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

//        Specification<Abc> spec = AbcRepository.Spec.instance().equal("chatRoom", "id", chatRoomId)
//                .and(AbcRepository.Spec.instance().equal("id", id));
//        abcRepository.delete(spec);

        abcRepository.deleteById(id);
    }
}
