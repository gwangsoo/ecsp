package com.example.bfi.web.rest;

import com.example.bfi.domain.dto.TariffDTO;
import com.example.bfi.exception.BadRequestAlertException;
import com.example.bfi.repository.TariffRepository;
import com.example.bfi.service.TariffService;
import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.ResponseUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.bfi.domain.entity.Tariff}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tariff/{country_code}/{party_id}")
public class TariffResource {

    private final Logger log = LoggerFactory.getLogger(TariffResource.class);

    private static final String ENTITY_NAME = "tariff";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final TariffService tariffService;

    private final TariffRepository tariffRepository;


    /**
     *
     * @param tariffDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("")
    public ResponseEntity<TariffDTO> createTariff(@Valid @RequestBody TariffDTO tariffDTO) throws URISyntaxException {
        log.debug("REST request to save Tariff : {}", tariffDTO);
        tariffDTO = tariffService.save(tariffDTO);
        return ResponseEntity.created(new URI("/api/tariff/" + tariffDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tariffDTO.getId()))
                .body(tariffDTO);
    }

    /**
     *
     * @param tariffId
     * @param tariffDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/{tariff_id}")
    public ResponseEntity<TariffDTO> updateTariff(
        @PathVariable(value = "tariff_id", required = true) final String tariffId,
        @PathVariable(value = "country_code", required = false) final String countryCode,
        @PathVariable(value = "party_id", required = false) final String partyId,
        @Valid @RequestBody TariffDTO tariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tariff : {}, {}", tariffId, tariffDTO);
        if (tariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tariffId, tariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tariffRepository.existsById(tariffId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tariffDTO = tariffService.update(tariffDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tariffDTO.getId()))
                .body(tariffDTO);
    }

    /**
     *
     * @param tariffId
     * @return
     * @throws URISyntaxException
     */
    @PatchMapping(value = "/{tariff_id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<TariffDTO> partialUpdateTariff(
            @PathVariable(value = "tariff_id", required = true) final String tariffId,
            @PathVariable(value = "country_code", required = false) final String countryCode,
            @PathVariable(value = "party_id", required = false) final String partyId,
            @NotNull @RequestBody TariffDTO tariffDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tariff partially : {}, {}", tariffId, tariffDTO);
        if (tariffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(tariffId, tariffDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!tariffRepository.existsById(tariffId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TariffDTO> result = tariffService.partialUpdate(tariffDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tariffDTO.getId())
        );
    }

    /**
     * {@code GET  /Tariffs} : get all the Tariffs.
     *
     * @param countryCode the id of the TariffDTO to retrieve.
     * @param partyId     the id of the TariffDTO to retrieve.
     * @param dateFrom    the date of the TariffDTO to retrieve.
     * @param dateTo      the date of the TariffDTO to retrieve.
     * @param offset      the offset of the TariffDTO to retrieve.
     * @param limit       the limit of the TariffDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Tariffs in body.
     */
    @GetMapping("")
    public List<TariffDTO> getAllTariffs(
            @PathVariable(value = "country_code", required = false) final String countryCode,
            @PathVariable(value = "party_id", required = false) final String partyId,
            @RequestParam(value = "date_from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime dateFrom,
            @RequestParam(value = "date_to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime dateTo,
            @RequestParam(value = "offset", required = false) final Integer offset,
            @RequestParam(value = "limit", required = false) final Integer limit) {
        log.debug("REST request to get all Tariffs, {},{},{},{}", dateFrom, dateTo, offset, limit);
        return tariffService.findAll(countryCode, partyId, ZonedDateTime.of(dateFrom, ZoneId.of("UTC")), ZonedDateTime.of(dateTo, ZoneId.of("UTC")), offset, limit);
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param tariffId
     * @return
     */
    @GetMapping({"/{tariff_id}"})
    public ResponseEntity<TariffDTO> getTariff(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "tariff_id", required = true) final String tariffId) {
        log.debug("REST request to get Tariff : {}/{}/{}/{}/{}", countryCode, partyId, tariffId);
        Optional<TariffDTO> TariffDTO = tariffService.findOne(tariffId);
        return ResponseUtil.wrapOrNotFound(TariffDTO);
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param tariffId
     * @return
     */
    @DeleteMapping("/{tariff_id}")
    public ResponseEntity<Void> deleteTariffs(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "tariff_id", required = true) final String tariffId) {
        log.debug("REST request to delete Tariffs : {}/{}/{}", countryCode, partyId, tariffId);
        tariffService.delete(tariffId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, tariffId)).build();
    }
}
