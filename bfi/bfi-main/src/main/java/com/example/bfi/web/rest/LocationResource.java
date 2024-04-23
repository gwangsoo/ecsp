package com.example.bfi.web.rest;

import com.example.bfi.exception.BadRequestAlertException;
import com.example.bfi.repository.LocationRepository;
import com.example.bfi.service.LocationService;
import com.example.bfi.domain.dto.LocationDTO;
import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.ResponseUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.example.bfi.domain.entity.Location}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/locations/{country_code}/{party_id}")
public class LocationResource {

    private final Logger log = LoggerFactory.getLogger(LocationResource.class);

    private static final String ENTITY_NAME = "location";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final LocationService locationService;

    private final LocationRepository locationRepository;

    /**
     * {@code POST  /locations} : Create a new location.
     *
     * @param locationDTO the locationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new locationDTO, or with status {@code 400 (Bad Request)} if the location has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LocationDTO> createLocation(@Valid @RequestBody LocationDTO locationDTO) throws URISyntaxException {
        log.debug("REST request to save Location : {}", locationDTO);
//        if (locationDTO.getId() != null) {
//            throw new BadRequestAlertException("A new location cannot already have an ID", ENTITY_NAME, "idexists");
//        }
        locationDTO = locationService.save(locationDTO);
        return ResponseEntity.created(new URI("/api/locations/" + locationDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, locationDTO.getId()))
                .body(locationDTO);
    }

    /**
     * {@code PUT  /locations/:id} : Updates an existing location.
     *
     * @param countryCode the id of the locationDTO to save.
     * @param partyId     the id of the locationDTO to save.
     * @param locationId  the id of the locationDTO to save.
     * @param locationDTO the locationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationDTO,
     * or with status {@code 400 (Bad Request)} if the locationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the locationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{location_id}/{evse_uid}/{connector_id}")
    public ResponseEntity<LocationDTO> updateLocation(
        @PathVariable(value = "country_code", required = true) final String countryCode,
        @PathVariable(value = "party_id", required = true) final String partyId,
        @PathVariable(value = "location_id", required = true) final String locationId,
        @PathVariable(value = "evse_uid", required = false) final String evseUid,
        @PathVariable(value = "connector_id", required = false) final String connectorId,
        @Valid @RequestBody LocationDTO locationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Location : {}/{}/{}/{}/{}, {}", countryCode, partyId, locationId, evseUid, connectorId, locationDTO);
        if (locationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(locationId, locationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationRepository.existsById(locationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        locationDTO = locationService.update(locationDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationDTO.getId()))
                .body(locationDTO);
    }

    /**
     * {@code PATCH  /locations/:id} : Partial updates given fields of an existing location, field will ignore if it is null
     *
     * @param countryCode the id of the locationDTO to save.
     * @param partyId     the id of the locationDTO to save.
     * @param locationId  the id of the locationDTO to save.
     * @param locationDTO the locationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated locationDTO,
     * or with status {@code 400 (Bad Request)} if the locationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the locationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the locationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{location_id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<LocationDTO> partialUpdateLocation(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "location_id", required = true) final String locationId,
            @NotNull @RequestBody LocationDTO locationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Location partially : {}/{}/{}, {}", countryCode, partyId, locationId, locationDTO);
        if (locationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(locationId, locationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!locationRepository.existsById(locationId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LocationDTO> result = locationService.partialUpdate(locationDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, locationDTO.getId())
        );
    }

    /**
     * {@code GET  /locations} : get all the locations.
     *
     * @param countryCode the id of the locationDTO to retrieve.
     * @param partyId     the id of the locationDTO to retrieve.
     * @param dateFrom    the date of the locationDTO to retrieve.
     * @param dateTo      the date of the locationDTO to retrieve.
     * @param offset      the offset of the locationDTO to retrieve.
     * @param limit       the limit of the locationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locations in body.
     */
    @GetMapping("")
    public List<LocationDTO> getAllLocations(
            @PathVariable(value = "country_code", required = false) final String countryCode,
            @PathVariable(value = "party_id", required = false) final String partyId,
            @RequestParam(value = "date_from", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime dateFrom,
            @RequestParam(value = "date_to", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") final LocalDateTime dateTo,
            @RequestParam(value = "offset", required = false) final Integer offset,
            @RequestParam(value = "limit", required = false) final Integer limit) {
        log.debug("REST request to get all Locations, {},{},{},{}", dateFrom, dateTo, offset, limit);
        return locationService.findAll(countryCode, partyId, ZonedDateTime.of(dateFrom, ZoneId.of("UTC")), ZonedDateTime.of(dateTo, ZoneId.of("UTC")), offset, limit);
    }

    /**
     * {@code GET  /locations/:id} : get the "id" location.
     *
     * @param countryCode the id of the locationDTO to retrieve.
     * @param partyId     the id of the locationDTO to retrieve.
     * @param locationId  the id of the locationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the locationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping({"/{location_id}", "/{location_id}/{evse_uid}", "/{location_id}/{evse_uid}/{connector_id}"})
    public ResponseEntity<LocationDTO> getLocation(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "location_id", required = true) final String locationId,
            @PathVariable(value = "evse_uid", required = false) final String evseUid,
            @PathVariable(value = "connector_id", required = false) final String connectorId) {
        log.debug("REST request to get Location : {}/{}/{}/{}/{}", countryCode, partyId, locationId, evseUid, connectorId);
        Optional<LocationDTO> locationDTO = locationService.findOne(locationId);
        return ResponseUtil.wrapOrNotFound(locationDTO);
    }

    /**
     * {@code DELETE  /locations/:id} : delete the "id" location.
     *
     * @param countryCode the id of the locationDTO to delete.
     * @param partyId     the id of the locationDTO to delete.
     * @param locationId  the id of the locationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{location_id}")
    public ResponseEntity<Void> deleteLocation(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "location_id", required = true) final String locationId) {
        log.debug("REST request to delete Location : {}/{}/{}", countryCode, partyId, locationId);
        locationService.delete(locationId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, locationId)).build();
    }
}
