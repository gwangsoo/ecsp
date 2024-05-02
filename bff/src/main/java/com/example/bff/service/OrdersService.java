package com.example.bff.service;

import com.example.orders.domain.dto.OrdersDTO;

import java.util.List;

public interface OrdersService {

    OrdersDTO createOrders(OrdersDTO ordersDTO);
    OrdersDTO partialUpdateOrders(String id, OrdersDTO ordersDTO);
    void deleteOrders(String id);
    List<OrdersDTO> getAllOrders(OrdersDTO.OrdersStatus status);
    OrdersDTO getOrders(String id);
}
