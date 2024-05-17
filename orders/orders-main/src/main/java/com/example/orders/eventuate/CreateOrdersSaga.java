package com.example.orders.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.eventuate.AbcTramMessageConfig;
import com.example.abc.eventuate.command.AbcRegisterCommand;
import com.example.ecsp.common.jpa.TenantContext;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.security.SecurityUtils;
import com.example.orders.service.AbcService;
import com.example.orders.service.XyzService;
import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.eventuate.XyzTramMessageConfig;
import com.example.xyz.eventuate.command.XyzRegisterCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Transactional
@RequiredArgsConstructor
@Component
@Slf4j
public class CreateOrdersSaga implements SimpleSaga<OrdersDTO> {

    private final EventuateSagaService eventuateSagaService;

    @Override
    public SagaDefinition<OrdersDTO> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private SagaDefinition<OrdersDTO> sagaDefinition =
            step()
              .invokeLocal(this::create)
              .withCompensation(this::reject)
            .step()
              .invokeParticipant(this::abcRegisterCommand)
//                .onReply(AbcDTO.class, this::handleAbcReply)
              .withCompensation(this::abcRegisterCommand4Close)
            .step()
              .invokeParticipant(this::xyzRegisterCommand)
//              .onReply(XyzDTO.class, this::handleXyzReply)
//              .onReply(Exception.class, this::handleAccountException)
            .step()
              .invokeLocal(this::approve)
            .build()
            ;

    public void create(OrdersDTO data) {
        log.info("SAGA create tenant={}", TenantContext.getCurrentTenant());
//        eventuateSagaService.create(data);
    }

    public void reject(OrdersDTO data) {
        log.info("SAGA reject tenant={}", TenantContext.getCurrentTenant());
        eventuateSagaService.reject(data);
    }

    public CommandWithDestination abcRegisterCommand(OrdersDTO data) {
        log.info("SAGA abcRegisterCommand tenant={}", TenantContext.getCurrentTenant());
        return send(new AbcRegisterCommand(AbcDTO.builder().id(data.getId()).data(data.getProductName()).size(data.getSize()).status(AbcDTO.AbcStatus.OPEN).build()))
                .withExtraHeaders(Map.of("tenant", TenantContext.getCurrentTenant().orElse("")))
                .to(AbcTramMessageConfig.commandChannel)
                .build();
    }

    public void handleAbcReply(OrdersDTO data, AbcDTO reply) {
        log.info("SAGA handleAbcReply tenant={}", TenantContext.getCurrentTenant());
        log.info("data = {}", data);
        log.info("reply = {}", reply);
        log.debug("handleAbcReply() token={}", SecurityUtils.getCurrentUserToken());

        try {
            AbcDTO abcDTO = eventuateSagaService.handleAbcReply(reply);
            log.info("abcDTO = {}", abcDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public CommandWithDestination abcRegisterCommand4Close(OrdersDTO data) {
        log.info("SAGA abcRegisterCommand4Close tenant={}", TenantContext.getCurrentTenant());
        return send(new AbcRegisterCommand(AbcDTO.builder().id(data.getId()).data(data.getProductName()).size(data.getSize()).status(AbcDTO.AbcStatus.CLOSE).build()))
                .withExtraHeaders(Map.of("tenant", TenantContext.getCurrentTenant().orElse("")))
                .to(AbcTramMessageConfig.commandChannel)
                .build();
    }

    public CommandWithDestination xyzRegisterCommand(OrdersDTO data) {
        log.info("SAGA xyzRegisterCommand tenant={}", TenantContext.getCurrentTenant());
        return send(new XyzRegisterCommand(XyzDTO.builder().id(data.getId()).name(data.getProductName()).status(XyzDTO.XyzStatus.ACTIVE).build()))
                .withExtraHeaders(Map.of("tenant", TenantContext.getCurrentTenant().orElse("")))
                .to(XyzTramMessageConfig.commandChannel)
                .build();
    }

    public void handleXyzReply(OrdersDTO data, XyzDTO reply) {
        log.info("SAGA handleXyzReply tenant={}", TenantContext.getCurrentTenant());
        log.info("data = {}", data);
        log.info("reply = {}", reply);

        try {
            XyzDTO xyzDTO = eventuateSagaService.handleXyzReply(reply);
            log.info("xyzDTO = {}", xyzDTO);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public void handleAccountException(OrdersDTO data, Exception reply) {
        log.info("SAGA handleAccountException tenant={}", TenantContext.getCurrentTenant());
        log.info(data.getId() + " - " + reply.toString());

        eventuateSagaService.handleAccountException(data, reply);
    }

    public void approve(OrdersDTO data) {
        log.info("SAGA approve tenant={}", TenantContext.getCurrentTenant());
        eventuateSagaService.approve(data);
    }
}
