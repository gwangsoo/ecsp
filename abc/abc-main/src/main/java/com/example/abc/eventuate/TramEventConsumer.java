package com.example.abc.eventuate;

import com.example.abc.service.AbcService;
import com.example.ecsp.common.jpa.TenantContext;
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
    private EventuateHandlerService eventuateHandlerService;

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
        log.info("handleXyzInserted tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }

    public void handleXyzUpdated(DomainEventEnvelope<XyzUpdateEvent> event) {
        log.info("handleXyzUpdated=" + event.getEvent().getXyz());
        log.info("handleXyzUpdated tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }

    public void handleXyzDeleted(DomainEventEnvelope<XyzDeleteEvent> event) {
        log.info("handleXyzDeleted=" + event.getEvent().getXyz());
        log.info("handleXyzDeleted tenant=" + event.getMessage().getHeader("tenant"));

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(event.getMessage().getHeader("tenant").orElse(null));

    }
}
