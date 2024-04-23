package com.example.orders.eventuate.event;

import com.example.orders.domain.dto.OrdersDTO;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersInsertEvent implements DomainEvent {
    private OrdersDTO orders;
}
