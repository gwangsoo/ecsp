package com.example.abc.web.rest;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.domain.entity.Abc;
import com.example.abc.exception.BadRequestAlertException;
import com.example.abc.repository.AbcRepository;
import com.example.abc.service.AbcService;
import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.PaginationUtil;
import com.example.ecsp.common.util.ResponseUtil;
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
 * REST controller for managing {@link com.example.abc.domain.entity.Abc}.
 */
@Tag(name = "Abc Domain")
@RestController
@RequestMapping("/api/abcs")
@Slf4j
@RequiredArgsConstructor
public class AbcResource {

    private static final String ENTITY_NAME = "abc";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final AbcService abcService;

    private final AbcRepository abcRepository;

    /**
     * {@code POST  /abcs} : Create a new abc.
     *
     * @param abcDTO the abcDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abcDTO, or with status {@code 400 (Bad Request)} if the abc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AbcDTO> createAbc(@Valid @RequestBody AbcDTO abcDTO)
        throws URISyntaxException {
        log.debug("REST request to save Abc : {}", abcDTO);

        AbcDTO result = abcService.save(abcDTO);
        return ResponseEntity
            .created(new URI("/api/abcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /abcs/:id} : Partial updates given fields of an existing abc, field will ignore if it is null
     *
     * @param id the id of the abcDTO to save.
     * @param abcDTO the abcDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abcDTO,
     * or with status {@code 400 (Bad Request)} if the abcDTO is not valid,
     * or with status {@code 404 (Not Found)} if the abcDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the abcDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbcDTO> partialUpdateAbc(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody AbcDTO abcDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Abc partially : {}, {}", id, abcDTO);

        if (abcDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abcDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abcRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbcDTO> result = abcService.partialUpdate(abcDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abcDTO.getId())
        );
    }

    /**
     * {@code GET  /abcs} : get all the abcs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abcs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AbcDTO>> getAllAbcs(
            @RequestParam(value = "status") final Abc.AbcStatus status,
            @PageableDefault(page = 0, size = 20, sort = "createdDate", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.debug("REST request to get all Abcs");

        final Page<AbcDTO> page = abcService.findAll(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /abcs/:id} : get the "id" abc.
     *
     * @param id the id of the abcDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abcDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AbcDTO> getAbc(
            @PathVariable String id) {
        log.debug("REST request to get Abc : {}", id);

        Optional<AbcDTO> abcDTO = abcService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abcDTO);
    }

    /**
     * {@code DELETE  /abcs/:id} : delete the "id" abc.
     *
     * @param id the id of the abcDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbc(@PathVariable String id) {
        log.debug("REST request to delete Abc : {}", id);

        abcService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
