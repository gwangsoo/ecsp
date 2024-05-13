package com.example.bfi.web.rest;

import com.example.bfi.domain.dto.VersionDTO;
import com.example.bfi.exception.BadRequestAlertException;
import com.example.bfi.repository.VersionRepository;
import com.example.bfi.service.VersionService;
import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.ResponseUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.bfi.domain.entity.Version}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/version/{country_code}/{party_id}")
public class VersionResource {

    private final Logger log = LoggerFactory.getLogger(VersionResource.class);

    private static final String ENTITY_NAME = "version";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final VersionService versionService;

    private final VersionRepository versionRepository;

    /**
     *
     * @param versionDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("")
    public ResponseEntity<VersionDTO> createVersion(@Valid @RequestBody VersionDTO versionDTO) throws URISyntaxException {
        log.debug("REST request to save Version : {}", versionDTO);
        versionDTO = versionService.save(versionDTO);
        return ResponseEntity.created(new URI("/api/version/" + versionDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, versionDTO.getId()))
                .body(versionDTO);
    }

    /**
     *
     * @param versionId
     * @param versionDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/{version_id}")
    public ResponseEntity<VersionDTO> updateVersion(
        @PathVariable(value = "version_id", required = true) final String versionId,
        @PathVariable(value = "country_code", required = false) final String countryCode,
        @PathVariable(value = "party_id", required = false) final String partyId,
        @Valid @RequestBody VersionDTO versionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Version : {}, {}", versionId, versionDTO);
        if (versionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(versionId, versionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!versionRepository.existsById(versionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        versionDTO = versionService.update(versionDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDTO.getId()))
                .body(versionDTO);
    }

    /**
     *
     * @param versionId
     * @return
     * @throws URISyntaxException
     */
    @PatchMapping(value = "/{version_id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<VersionDTO> partialUpdateVersion(
            @PathVariable(value = "version_id", required = true) final String versionId,
            @PathVariable(value = "country_code", required = false) final String countryCode,
            @PathVariable(value = "party_id", required = false) final String partyId,
            @NotNull @RequestBody VersionDTO versionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Version partially : {}, {}", versionId, versionDTO);
        if (versionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(versionId, versionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!versionRepository.existsById(versionId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VersionDTO> result = versionService.partialUpdate(versionDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDTO.getId())
        );
    }

    /**
     * {@code GET  /Versions} : get all the Versions.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Versions in body.
     */
    @GetMapping("")
    public List<VersionDTO> getAllVersions() {
        log.debug("REST request to get all Versions");
        return versionService.findAll();
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param versionId
     * @return
     */
    @GetMapping({"/{version_id}"})
    public ResponseEntity<VersionDTO> getVersion(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "version_id", required = true) final String versionId) {
        log.debug("REST request to get Version : {}/{}/{}/{}/{}", countryCode, partyId, versionId);
        Optional<VersionDTO> VersionDTO = versionService.findOne(versionId);
        return ResponseUtil.wrapOrNotFound(VersionDTO);
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param versionId
     * @return
     */
    @DeleteMapping("/{version_id}")
    public ResponseEntity<Void> deleteVersions(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "version_id", required = true) final String versionId) {
        log.debug("REST request to delete Versions : {}/{}/{}", countryCode, partyId, versionId);
        versionService.delete(versionId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, versionId)).build();
    }
}
