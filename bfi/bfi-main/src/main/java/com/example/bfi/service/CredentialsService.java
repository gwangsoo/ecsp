package com.example.bfi.service;

import com.example.bfi.domain.dto.CredentialsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.bfi.domain.entity.Credentials}.
 */
public interface CredentialsService {

    /**
     *
     * @param credentialsDTO
     * @return
     */
    CredentialsDTO save(CredentialsDTO credentialsDTO);

    /**
     *
     * @param credentialsDTO
     * @return
     */
    CredentialsDTO update(CredentialsDTO credentialsDTO);

    /**
     *
     * @param credentialsDTO
     * @return
     */
    Optional<CredentialsDTO> partialUpdate(CredentialsDTO credentialsDTO);

    /**
     *
     * @param id
     * @return
     */
    Optional<CredentialsDTO> findOne(String id);

    List<CredentialsDTO> findAll();

    /**
     *
     * @param id
     */
    void delete(String id);
}
