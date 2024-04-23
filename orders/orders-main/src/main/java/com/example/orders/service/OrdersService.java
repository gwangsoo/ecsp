package com.example.orders.service;

import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.example.orders.domain.dto.OrdersDTO}.
 */
public interface OrdersService {
    /**
     * Save a announcement.
     *
     * @param OrdersDTO the entity to save.
     * @return the persisted entity.
     */
    OrdersDTO save(OrdersDTO OrdersDTO);

    /**
     * Updates a announcement.
     *
     * @param OrdersDTO the entity to update.
     * @return the persisted entity.
     */
    OrdersDTO update(OrdersDTO OrdersDTO);

    /**
     * Partially updates a announcement.
     *
     * @param OrdersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdersDTO> partialUpdate(OrdersDTO OrdersDTO);

    /**
     * Get all the announcements.
     *
     * @return the list of entities.
     */
    Page<OrdersDTO> findAll(OrdersDTO.OrdersStatus ordersStatus, Pageable pageable);

    /**
     * Get the "id" announcement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdersDTO> findOne(String id);

    /**
     * Delete the "id" announcement.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
