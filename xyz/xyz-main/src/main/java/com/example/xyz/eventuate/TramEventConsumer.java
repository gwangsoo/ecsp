package com.example.xyz.eventuate;

import com.example.abc.eventuate.event.AbcDeleteEvent;
import com.example.abc.eventuate.event.AbcInsertEvent;
import com.example.abc.eventuate.event.AbcUpdateEvent;
import com.example.xyz.eventuate.event.XyzDeleteEvent;
import com.example.xyz.eventuate.event.XyzInsertEvent;
import com.example.xyz.eventuate.event.XyzUpdateEvent;
import com.example.xyz.service.XyzService;
import io.eventuate.tram.events.subscriber.DomainEventEnvelope;
import io.eventuate.tram.events.subscriber.DomainEventHandlers;
import io.eventuate.tram.events.subscriber.DomainEventHandlersBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.DeleteEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class TramEventConsumer {

    private String aggregateType;

    public TramEventConsumer(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    @Autowired
    private XyzService xyzService;

    public DomainEventHandlers domainEventHandlers() {
        return DomainEventHandlersBuilder
                .forAggregateType(aggregateType)
                .onEvent(AbcInsertEvent.class, this::handleAbcInserted)
                .onEvent(AbcUpdateEvent.class, this::handleAbcUpdated)
                .onEvent(AbcDeleteEvent.class, this::handleAbcDeleted)
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
}
