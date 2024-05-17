package com.example.bfi.web.rest;

import com.example.bfi.domain.dto.CredentialsDTO;
import com.example.bfi.exception.BadRequestAlertException;
import com.example.bfi.repository.CredentialsRepository;
import com.example.bfi.service.CredentialsService;
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
 * REST controller for managing {@link com.example.bfi.domain.entity.Credentials}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/credentials/{country_code}/{party_id}")
public class CredentialsResource {

    private final Logger log = LoggerFactory.getLogger(CredentialsResource.class);

    private static final String ENTITY_NAME = "credentials";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final CredentialsService CredentialsService;

    private final CredentialsRepository CredentialsRepository;

    /**
     *
     * @param versionDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("")
    public ResponseEntity<CredentialsDTO> createCredentials(@Valid @RequestBody CredentialsDTO versionDTO) throws URISyntaxException {
        log.debug("REST request to save Credentials : {}", versionDTO);
        versionDTO = CredentialsService.save(versionDTO);
        return ResponseEntity.created(new URI("/api/version/" + versionDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, versionDTO.getId()))
                .body(versionDTO);
    }

    /**
     *
     * @param credentialsId
     * @param versionDTO
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/{credentials_id}")
    public ResponseEntity<CredentialsDTO> updateCredentials(
        @PathVariable(value = "credentials_id", required = true) final String credentialsId,
        @PathVariable(value = "country_code", required = false) final String countryCode,
        @PathVariable(value = "party_id", required = false) final String partyId,
        @Valid @RequestBody CredentialsDTO versionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Credentials : {}, {}", credentialsId, versionDTO);
        if (versionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(credentialsId, versionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!CredentialsRepository.existsById(credentialsId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        versionDTO = CredentialsService.update(versionDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDTO.getId()))
                .body(versionDTO);
    }

    /**
     *
     * @param credentialsId
     * @return
     * @throws URISyntaxException
     */
    @PatchMapping(value = "/{credentials_id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<CredentialsDTO> partialUpdateCredentials(
            @PathVariable(value = "credentials_id", required = true) final String credentialsId,
            @PathVariable(value = "country_code", required = false) final String countryCode,
            @PathVariable(value = "party_id", required = false) final String partyId,
            @NotNull @RequestBody CredentialsDTO versionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Credentials partially : {}, {}", credentialsId, versionDTO);
        if (versionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(credentialsId, versionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!CredentialsRepository.existsById(credentialsId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CredentialsDTO> result = CredentialsService.partialUpdate(versionDTO);

        return ResponseUtil.wrapOrNotFound(
                result,
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, versionDTO.getId())
        );
    }

    /**
     * {@code GET  /Credentialss} : get all the Credentialss.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Credentialss in body.
     */
    @GetMapping("")
    public List<CredentialsDTO> getAllCredentialss() {
        log.debug("REST request to get all Credentialss");
        return CredentialsService.findAll();
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param credentialsId
     * @return
     */
    @GetMapping({"/{credentials_id}"})
    public ResponseEntity<CredentialsDTO> getCredentials(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "credentials_id", required = true) final String credentialsId) {
        log.debug("REST request to get Credentials : {}/{}/{}/{}/{}", countryCode, partyId, credentialsId);
        Optional<CredentialsDTO> CredentialsDTO = CredentialsService.findOne(credentialsId);
        return ResponseUtil.wrapOrNotFound(CredentialsDTO);
    }

    /**
     *
     * @param countryCode
     * @param partyId
     * @param credentialsId
     * @return
     */
    @DeleteMapping("/{Credentials_id}")
    public ResponseEntity<Void> deleteCredentialss(
            @PathVariable(value = "country_code", required = true) final String countryCode,
            @PathVariable(value = "party_id", required = true) final String partyId,
            @PathVariable(value = "credentials_id", required = true) final String credentialsId) {
        log.debug("REST request to delete Credentialss : {}/{}/{}", countryCode, partyId, credentialsId);
        CredentialsService.delete(credentialsId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, credentialsId)).build();
    }
}
