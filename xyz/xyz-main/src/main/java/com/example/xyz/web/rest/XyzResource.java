package com.example.xyz.web.rest;

import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.PaginationUtil;
import com.example.ecsp.common.util.ResponseUtil;
import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.exception.BadRequestAlertException;
import com.example.xyz.repository.XyzRepository;
import com.example.xyz.service.XyzService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.example.xyz.domain.entity.Xyz}.
 */
@Tag(name = "Xyz Domain")
@RestController
@RequestMapping("/api/xyzs")
@Slf4j
@RequiredArgsConstructor
public class XyzResource {

    private static final String ENTITY_NAME = "xyz";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final XyzService xyzService;

    private final XyzRepository xyzRepository;

    /**
     * {@code POST  /xyzs} : Create a new xyz.
     *
     * @param xyzDTO the xyzDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new xyzDTO, or with status {@code 400 (Bad Request)} if the xyz has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<XyzDTO> createXyz(@Valid @RequestBody XyzDTO xyzDTO)
        throws URISyntaxException {
        log.debug("REST request to save Xyz : {}", xyzDTO);

        XyzDTO result = xyzService.save(xyzDTO);
        return ResponseEntity
            .created(new URI("/api/xyzs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /xyzs/:id} : Partial updates given fields of an existing xyz, field will ignore if it is null
     *
     * @param id the id of the xyzDTO to save.
     * @param xyzDTO the xyzDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated xyzDTO,
     * or with status {@code 400 (Bad Request)} if the xyzDTO is not valid,
     * or with status {@code 404 (Not Found)} if the xyzDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the xyzDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<XyzDTO> partialUpdateXyz(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody XyzDTO xyzDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Xyz partially : {}, {}", id, xyzDTO);

        if (xyzDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, xyzDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!xyzRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<XyzDTO> result = xyzService.partialUpdate(xyzDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, xyzDTO.getId())
        );
    }

    /**
     * {@code GET  /xyzs} : get all the xyzs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of xyzs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<XyzDTO>> getAllXyzs(
            @RequestParam(value = "attrValue") final String attrValue,
            @RequestParam(value = "status") final Xyz.XyzStatus status,
            @PageableDefault(page = 0, size = 20, sort = "createdDate", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.debug("REST request to get all Xyzs");

        final Page<XyzDTO> page = xyzService.findAll(attrValue, status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /xyzs/:id} : get the "id" xyz.
     *
     * @param id the id of the xyzDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the xyzDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<XyzDTO> getXyz(
            @PathVariable String id) {
        log.debug("REST request to get Xyz : {}", id);

        Optional<XyzDTO> xyzDTO = xyzService.findOne(id);
        return ResponseUtil.wrapOrNotFound(xyzDTO);
    }

    /**
     * {@code DELETE  /xyzs/:id} : delete the "id" xyz.
     *
     * @param id the id of the xyzDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteXyz(@PathVariable String id) {
        log.debug("REST request to delete Xyz : {}", id);

        xyzService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
