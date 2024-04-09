package com.example.orders.eventuate.event;

import com.example.orders.domain.entity.Orders;
import io.eventuate.tram.events.common.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDeleteEvent implements DomainEvent {
    private Orders orders;
}
