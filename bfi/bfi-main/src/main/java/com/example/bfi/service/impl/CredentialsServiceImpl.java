package com.example.bfi.service.impl;

import com.example.bfi.domain.dto.CredentialsDTO;
import com.example.bfi.domain.entity.Credentials;
import com.example.bfi.repository.CredentialsRepository;
import com.example.bfi.service.CredentialsService;
import com.example.bfi.service.mapper.CredentialsMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Credentials}.
 */
@RequiredArgsConstructor
@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final Logger log = LoggerFactory.getLogger(CredentialsServiceImpl.class);

    private final CredentialsRepository CredentialsRepository;

    private final CredentialsMapper credentialsMapper;

    @Override
    public CredentialsDTO save(CredentialsDTO tariffDTO) {
        log.debug("Request to save Credentials : {}", tariffDTO);
        Credentials tariff = credentialsMapper.toEntity(tariffDTO);
        tariff = CredentialsRepository.save(tariff);
        return credentialsMapper.toDto(tariff);
    }

    @Override
    public CredentialsDTO update(CredentialsDTO tariffDTO) {
        log.debug("Request to update Credentials : {}", tariffDTO);
        Credentials tariff = credentialsMapper.toEntity(tariffDTO);
        tariff = CredentialsRepository.save(tariff);
        return credentialsMapper.toDto(tariff);
    }

    @Override
    public Optional<CredentialsDTO> partialUpdate(CredentialsDTO tariffDTO) {
        log.debug("Request to partially update Credentials : {}", tariffDTO);

        return CredentialsRepository
            .findById(tariffDTO.getId())
            .map(existingCredentials -> {
                credentialsMapper.partialUpdate(existingCredentials, tariffDTO);

                return existingCredentials;
            })
            .map(CredentialsRepository::save)
            .map(credentialsMapper::toDto);
    }

    @Override
    public List<CredentialsDTO> findAll() {
        log.debug("Request to get all Credentialss");
        return CredentialsRepository.findAll().stream().map(credentialsMapper::toDto).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<CredentialsDTO> findOne(String id) {
        log.debug("Request to get Credentials : {}", id);
        return CredentialsRepository.findById(id).map(credentialsMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Credentials : {}", id);
        CredentialsRepository.deleteById(id);
    }
}
