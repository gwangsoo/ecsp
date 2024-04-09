package com.example.orders.service.mapper;

import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity {@link Orders} and its DTO {@link OrdersDTO}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdersMapper extends com.example.orders.service.mapper.EntityMapper<OrdersDTO, Orders> {}
