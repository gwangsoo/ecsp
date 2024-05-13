package com.example.bfi.service;

import com.example.bfi.domain.dto.VersionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.bfi.domain.entity.Version}.
 */
public interface VersionService {

    /**
     *
     * @param versionDTO
     * @return
     */
    VersionDTO save(VersionDTO versionDTO);

    /**
     *
     * @param versionDTO
     * @return
     */
    VersionDTO update(VersionDTO versionDTO);

    /**
     *
     * @param versionDTO
     * @return
     */
    Optional<VersionDTO> partialUpdate(VersionDTO versionDTO);

    /**
     *
     * @param id
     * @return
     */
    Optional<VersionDTO> findOne(String id);

    List<VersionDTO> findAll();

    /**
     *
     * @param id
     */
    void delete(String id);
}
