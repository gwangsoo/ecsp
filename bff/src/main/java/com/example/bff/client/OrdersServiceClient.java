package com.example.bff.client;

import com.example.orders.domain.dto.OrdersDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

@Headers("Authorization: Bearer {oidcToken}")
public interface OrdersServiceClient {
    @RequestLine("POST /services/orders/api/orders")
    @Headers("Content-Type: application/json")
    OrdersDTO createOrders(@Param("oidcToken") String oidcToken, OrdersDTO ordersDTO);

    @RequestLine("PATCH /services/orders/api/orders/{id}")
    @Headers("Content-Type: application/json")
    OrdersDTO partialUpdateOrders(@Param("oidcToken") String oidcToken, @Param("id") String id, OrdersDTO ordersDTO);

    @RequestLine("DELETE /services/orders/api/orders/{id}")
    @Headers("Content-Type: application/json")
    void deleteOrders(@Param("oidcToken") String oidcToken, @Param("id") String id);

    @RequestLine("GET /services/orders/api/orders")
    @Headers("Content-Type: application/json")
    List<OrdersDTO> getAllOrders(@Param("oidcToken") String oidcToken, OrdersDTO.OrdersStatus status);

    @RequestLine("GET /services/orders/api/orders/{id}")
    @Headers("Content-Type: application/json")
    OrdersDTO getOrders(@Param("oidcToken") String oidcToken, @Param("id") String id);
}
