package com.example.bff.web.rest;

import com.example.bff.security.SecurityUtils;
import com.example.orders.domain.dto.OrdersDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.bff.security.SecurityUtils.extractToken;

/**
 * REST controller for managing composite.
 */
@Tag(name = "Composite API")
@RestController
@RequestMapping("/api/composite")
@Slf4j
@RequiredArgsConstructor
public class CompositeResource {

    private static final String ENTITY_NAME = "orders";

    @Value("${application.clientApp.name}")
    private String applicationName;

    private final com.example.bff.service.OrdersService ordersService;
    private final com.example.bff.service.AbcService abcService;
    private final com.example.bff.service.XyzService xyzService;

//    /**
//     * {@code POST  /orders} : Create a new orders.
//     *
//     * @param ordersDTO the ordersDTO to create.
//     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordersDTO, or with status {@code 400 (Bad Request)} if the orders has already an ID.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PostMapping("")
//    public ResponseEntity<OrdersDTO> createOrders(@Valid @RequestBody OrdersDTO ordersDTO)
//        throws URISyntaxException {
//        log.debug("REST request to save Orders : {}", ordersDTO);
//
//        OrdersDTO result = ordersService.save(ordersDTO);
//        return ResponseEntity
//            .created(new URI("/api/orders/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
//            .body(result);
//    }
//
//    /**
//     * {@code PATCH  /orders/:id} : Partial updates given fields of an existing orders, field will ignore if it is null
//     *
//     * @param id the id of the ordersDTO to save.
//     * @param ordersDTO the ordersDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordersDTO,
//     * or with status {@code 400 (Bad Request)} if the ordersDTO is not valid,
//     * or with status {@code 404 (Not Found)} if the ordersDTO is not found,
//     * or with status {@code 500 (Internal Server Error)} if the ordersDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
//    public ResponseEntity<OrdersDTO> partialUpdateOrders(
//        @PathVariable(value = "id", required = false) final String id,
//        @NotNull @RequestBody OrdersDTO ordersDTO
//    ) throws URISyntaxException {
//        log.debug("REST request to partial update Orders partially : {}, {}", id, ordersDTO);
//
//        if (ordersDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        if (!Objects.equals(id, ordersDTO.getId())) {
//            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
//        }
//
//        if (!ordersRepository.existsById(id)) {
//            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
//        }
//
//        Optional<OrdersDTO> result = ordersService.partialUpdate(ordersDTO);
//
//        return ResponseUtil.wrapOrNotFound(
//            result,
//            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordersDTO.getId())
//        );
//    }
//
    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OrdersDTO>> getAllOrders(Authentication authentication,
                                                        @RequestParam(value = "status") final OrdersDTO.OrdersStatus status) {
        log.info("CHECKER = {}", extractToken(authentication));
        log.debug("REST request to get all Orders");
        log.debug("request={}", ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication).block());
        log.debug("request2={}", SecurityContextHolder.getContext().getAuthentication());
        log.debug("getAllOrders={}", SecurityUtils.getCurrentUserToken().block());
        return ResponseEntity.ok(ordersService.getAllOrders(status));
    }
//            @PageableDefault(page = 0, size = 20, sort = "createdDate", direction = Sort.Direction.ASC)
//            Pageable pageable) {
//        log.debug("REST request to get all Orderss");
//
//        final Page<OrdersDTO> page = ordersService.findAll(status, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * {@code GET  /orders/:id} : get the "id" orders.
//     *
//     * @param id the id of the ordersDTO to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordersDTO, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/{id}")
//    public ResponseEntity<OrdersDTO> getOrders(
//            @PathVariable String id) {
//        log.debug("REST request to get Orders : {}", id);
//
//        Optional<OrdersDTO> ordersDTO = ordersService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(ordersDTO);
//    }
//
//    /**
//     * {@code DELETE  /orders/:id} : delete the "id" orders.
//     *
//     * @param id the id of the ordersDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOrders(@PathVariable String id) {
//        log.debug("REST request to delete Orders : {}", id);
//
//        ordersService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
//    }
}
