package com.example.orders.eventuate;

import com.example.abc.eventuate.event.AbcDeleteEvent;
import com.example.abc.eventuate.event.AbcInsertEvent;
import com.example.abc.eventuate.event.AbcUpdateEvent;
import com.example.xyz.eventuate.event.XyzDeleteEvent;
import com.example.xyz.eventuate.event.XyzInsertEvent;
import com.example.xyz.eventuate.event.XyzUpdateEvent;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TramEventConsumer {

    private String aggregateType;

    public TramEventConsumer(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Autowired
    private com.example.orders.service.OrdersService ordersService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(aggregateType)
                .onEvent(AbcInsertEvent.class, this::handleAbcInserted)
                .onEvent(AbcUpdateEvent.class, this::handleAbcUpdated)
                .onEvent(AbcDeleteEvent.class, this::handleAbcDeleted)
                .onEvent(XyzInsertEvent.class, this::handleXyzInserted)
                .onEvent(XyzUpdateEvent.class, this::handleXyzUpdated)
                .onEvent(XyzDeleteEvent.class, this::handleXyzDeleted)
                .build();
    }

    public void handleAbcInserted(DomainEventEnvelope<AbcInsertEvent> event) {
        log.info("handleAbcInserted=" + event.getEvent().getAbc());
    }

    public void handleAbcUpdated(DomainEventEnvelope<AbcUpdateEvent> event) {
        log.info("handleAbcUpdated=" + event.getEvent().getAbc());
    }

    public void handleAbcDeleted(DomainEventEnvelope<AbcDeleteEvent> event) {
        log.info("handleAbcDeleted=" + event.getEvent().getAbc());
    }

    public void handleXyzInserted(DomainEventEnvelope<XyzInsertEvent> event) {
        log.info("handleXyzInserted=" + event.getEvent().getXyz());
    }

    public void handleXyzUpdated(DomainEventEnvelope<XyzUpdateEvent> event) {
        log.info("handleXyzUpdated=" + event.getEvent().getXyz());
    }

    public void handleXyzDeleted(DomainEventEnvelope<XyzDeleteEvent> event) {
        log.info("handleXyzDeleted=" + event.getEvent().getXyz());
    }
}
