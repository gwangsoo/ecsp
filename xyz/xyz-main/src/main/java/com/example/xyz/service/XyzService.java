package com.example.xyz.service;

import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.entity.Xyz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.xyz.domain.dto.XyzDTO}.
 */
public interface XyzService {
    /**
     * Save a announcement.
     *
     * @param XyzDTO the entity to save.
     * @return the persisted entity.
     */
    XyzDTO save(XyzDTO XyzDTO);

    /**
     * Updates a announcement.
     *
     * @param XyzDTO the entity to update.
     * @return the persisted entity.
     */
    XyzDTO update(XyzDTO XyzDTO);

    /**
     * Partially updates a announcement.
     *
     * @param XyzDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<XyzDTO> partialUpdate(XyzDTO XyzDTO);

    /**
     * Get all the announcements.
     *
     * @return the list of entities.
     */
    Page<XyzDTO> findAll(Xyz.XyzStatus xyzStatus, Pageable pageable);

    /**
     * Get the "id" announcement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<XyzDTO> findOne(String id);

    /**
     * Delete the "id" announcement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
