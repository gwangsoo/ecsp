package com.example.bfi.service;

import com.example.bfi.domain.dto.TariffDTO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.bfi.domain.entity.Tariff}.
 */
public interface TariffService {
    /**
     * Save a version.
     *
     * @param TariffDTO the entity to save.
     * @return the persisted entity.
     */
    TariffDTO save(TariffDTO TariffDTO);

    /**
     * Updates a version.
     *
     * @param TariffDTO the entity to update.
     * @return the persisted entity.
     */
    TariffDTO update(TariffDTO TariffDTO);

    /**
     * Partially updates a version.
     *
     * @param TariffDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TariffDTO> partialUpdate(TariffDTO TariffDTO);

    /**
     * Get all the versions.
     *
     * @return the list of entities.
     */
    List<TariffDTO> findAll(String countryCode, String partyId, ZonedDateTime dateFrom, ZonedDateTime dateTo, Integer offset, Integer limit);

    /**
     * Get the "id" version.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TariffDTO> findOne(String id);

    /**
     * Delete the "id" version.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
