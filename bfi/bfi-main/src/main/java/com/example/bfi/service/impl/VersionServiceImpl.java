package com.example.bfi.service.impl;

import com.example.bfi.domain.dto.VersionDTO;
import com.example.bfi.domain.entity.Version;
import com.example.bfi.repository.VersionRepository;
import com.example.bfi.service.VersionService;
import com.example.bfi.service.mapper.VersionMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link com.example.bfi.domain.entity.Version}.
 */
@RequiredArgsConstructor
@Service
public class VersionServiceImpl implements VersionService {

    private final Logger log = LoggerFactory.getLogger(VersionServiceImpl.class);

    private final VersionRepository versionRepository;

    private final VersionMapper versionMapper;

    @Override
    public VersionDTO save(VersionDTO tariffDTO) {
        log.debug("Request to save Version : {}", tariffDTO);
        Version tariff = versionMapper.toEntity(tariffDTO);
        tariff = versionRepository.save(tariff);
        return versionMapper.toDto(tariff);
    }

    @Override
    public VersionDTO update(VersionDTO tariffDTO) {
        log.debug("Request to update Version : {}", tariffDTO);
        Version tariff = versionMapper.toEntity(tariffDTO);
        tariff = versionRepository.save(tariff);
        return versionMapper.toDto(tariff);
    }

    @Override
    public Optional<VersionDTO> partialUpdate(VersionDTO tariffDTO) {
        log.debug("Request to partially update Version : {}", tariffDTO);

        return versionRepository
            .findById(tariffDTO.getId())
            .map(existingVersion -> {
                versionMapper.partialUpdate(existingVersion, tariffDTO);

                return existingVersion;
            })
            .map(versionRepository::save)
            .map(versionMapper::toDto);
    }

    @Override
    public List<VersionDTO> findAll() {
        log.debug("Request to get all Versions");
        return versionRepository.findAll().stream().map(versionMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<VersionDTO> findOne(String id) {
        log.debug("Request to get Version : {}", id);
        return versionRepository.findById(id).map(versionMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Version : {}", id);
        versionRepository.deleteById(id);
    }
}
