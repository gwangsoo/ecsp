package com.example.orders.web.rest;

import com.example.ecsp.common.util.HeaderUtil;
import com.example.ecsp.common.util.PaginationUtil;
import com.example.ecsp.common.util.ResponseUtil;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.exception.BadRequestAlertException;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.AbcService;
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
 * REST controller for managing {@link com.example.orders.domain.entity.Orders}.
 */
@Tag(name = "Orders Domain")
@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrdersResource {

    private static final String ENTITY_NAME = "orders";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final com.example.orders.service.OrdersService ordersService;

    private final OrdersRepository ordersRepository;

    /**
     * {@code POST  /orders} : Create a new orders.
     *
     * @param ordersDTO the ordersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordersDTO, or with status {@code 400 (Bad Request)} if the orders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrdersDTO> createOrders(@Valid @RequestBody OrdersDTO ordersDTO)
        throws URISyntaxException {
        log.debug("REST request to save Orders : {}", ordersDTO);

        OrdersDTO result = ordersService.save(ordersDTO);
        return ResponseEntity
            .created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /orders/:id} : Partial updates given fields of an existing orders, field will ignore if it is null
     *
     * @param id the id of the ordersDTO to save.
     * @param ordersDTO the ordersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordersDTO,
     * or with status {@code 400 (Bad Request)} if the ordersDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ordersDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdersDTO> partialUpdateOrders(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody OrdersDTO ordersDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Orders partially : {}, {}", id, ordersDTO);

        if (ordersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordersDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordersRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdersDTO> result = ordersService.partialUpdate(ordersDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordersDTO.getId())
        );
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrdersDTO>> getAllOrderss(
            @RequestParam(value = "status") final OrdersDTO.OrdersStatus status,
            @PageableDefault(page = 0, size = 20, sort = "createdDate", direction = Sort.Direction.ASC)
            Pageable pageable) {
        log.debug("REST request to get all Orderss");

        final Page<OrdersDTO> page = ordersService.findAll(status, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private final AbcService abcService;

    /**
     * {@code GET  /orders/:id} : get the "id" orders.
     *
     * @param id the id of the ordersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdersDTO> getOrders(
            @PathVariable String id) {
        log.debug("REST request to get Orders : {}", id);

//        AbcDTO abcDTO = abcService.getAbc(id);

        Optional<OrdersDTO> ordersDTO = ordersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordersDTO);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" orders.
     *
     * @param id the id of the ordersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable String id) {
        log.debug("REST request to delete Orders : {}", id);

        ordersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
