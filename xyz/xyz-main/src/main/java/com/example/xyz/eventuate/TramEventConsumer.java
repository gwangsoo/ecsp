package com.example.xyz.eventuate;

import com.example.abc.eventuate.event.AbcDeleteEvent;
import com.example.abc.eventuate.event.AbcInsertEvent;
import com.example.abc.eventuate.event.AbcUpdateEvent;
import com.example.ecsp.common.jpa.TenantContext;
import com.example.xyz.service.XyzService;
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
    private EventuateHandlerService eventuateHandlerService;

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
        log.info("handleAbcInserted tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }

    public void handleAbcUpdated(DomainEventEnvelope<AbcUpdateEvent> event) {
        log.info("handleAbcUpdated=" + event.getEvent().getAbc());
        log.info("handleAbcUpdated tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }

    public void handleAbcDeleted(DomainEventEnvelope<AbcDeleteEvent> event) {
        log.info("handleAbcDeleted=" + event.getEvent().getAbc());
        log.info("handleAbcDeleted tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }
}
