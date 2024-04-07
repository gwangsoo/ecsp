package com.example.abc.eventuate;

import com.example.abc.service.AbcService;
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
    private AbcService abcService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(aggregateType)
                .onEvent(XyzInsertEvent.class, this::handleXyzInserted)
                .onEvent(XyzUpdateEvent.class, this::handleXyzUpdated)
                .onEvent(XyzDeleteEvent.class, this::handleXyzDeleted)
                .build();
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
