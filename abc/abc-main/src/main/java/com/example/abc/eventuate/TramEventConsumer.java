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
                .onEvent(XyzInsertEvent.class, this::handleBoardInserted)
                .onEvent(XyzUpdateEvent.class, this::handleBoardUpdated)
                .onEvent(XyzDeleteEvent.class, this::handleBoardDeleted)
                .build();
    }

    public void handleBoardInserted(DomainEventEnvelope<XyzInsertEvent> event) {
        log.info("handleBoardInserted=" + event.getEvent().getXyz());
    }

    public void handleBoardUpdated(DomainEventEnvelope<XyzUpdateEvent> event) {
        log.info("handleBoardUpdated=" + event.getEvent().getXyz());
    }

    public void handleBoardDeleted(DomainEventEnvelope<XyzDeleteEvent> event) {
        log.info("handleBoardDeleted=" + event.getEvent().getXyz());
    }
}
