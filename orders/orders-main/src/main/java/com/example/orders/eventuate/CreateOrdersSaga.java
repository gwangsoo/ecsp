package com.example.orders.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.domain.entity.Abc;
import com.example.abc.eventuate.AbcTramMessageConfig;
import com.example.abc.eventuate.command.AbcRegisterCommand;
import com.example.orders.domain.entity.Orders;
import com.example.orders.repository.OrdersRepository;
import com.example.orders.service.AbcService;
import com.example.orders.service.XyzService;
import com.example.orders.service.impl.XyzServiceImpl;
import com.example.xyz.domain.dto.XyzDTO;
import com.example.xyz.domain.entity.Xyz;
import com.example.xyz.eventuate.XyzTramMessageConfig;
import com.example.xyz.eventuate.command.XyzRegisterCommand;
import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

@Transactional
@RequiredArgsConstructor
@Component
@Slf4j
public class CreateOrdersSaga implements SimpleSaga<Orders> {
    private final OrdersRepository ordersRepository;

    private final AbcService abcService;
    private final XyzService xyzService;

    @Override
    public SagaDefinition<Orders> getSagaDefinition() {
        return this.sagaDefinition;
    }

    private SagaDefinition<Orders> sagaDefinition =
            step()
              .invokeLocal(this::create)
              .withCompensation(this::reject)
            .step()
              .invokeParticipant(this::abcRegisterCommand)
                .onReply(AbcDTO.class, this::handleAbcReply)
              .withCompensation(this::abcRegisterCommand4Close)
            .step()
              .invokeParticipant(this::xyzRegisterCommand)
              .onReply(XyzDTO.class, this::handleXyzReply)
              .onReply(Exception.class, this::handleAccountException)
            .step()
              .invokeLocal(this::approve)
            .build()
            ;

    public void create(Orders data) {
        ordersRepository.save(data);
    }

    public void reject(Orders data) {
        ordersRepository.findById(data.getId()).get().setStatus(Orders.OrdersStatus.REJECTED);
    }

    public CommandWithDestination abcRegisterCommand(Orders data) {
        return send(new AbcRegisterCommand(AbcDTO.builder().id(data.getId()).data(data.getProductName()).size(data.getSize()).status(Abc.AbcStatus.OPEN).build()))
                .to(AbcTramMessageConfig.commandChannel)
                .build();
    }

    public void handleAbcReply(Orders data, AbcDTO reply) {
        log.info("data = {}", data);
        log.info("reply = {}", reply);

        AbcDTO abcDTO = abcService.getAbc(reply.getId());

        log.info("abcDTO = {}", abcDTO);
    }

    public CommandWithDestination abcRegisterCommand4Close(Orders data) {
        return send(new AbcRegisterCommand(AbcDTO.builder().id(data.getId()).data(data.getProductName()).size(data.getSize()).status(Abc.AbcStatus.CLOSE).build()))
                .to(AbcTramMessageConfig.commandChannel)
                .build();
    }

    public CommandWithDestination xyzRegisterCommand(Orders data) {
        return send(new XyzRegisterCommand(XyzDTO.builder().id(data.getId()).name(data.getProductName()).status(Xyz.XyzStatus.ACTIVE).build()))
                .to(XyzTramMessageConfig.commandChannel)
                .build();
    }

    public void handleXyzReply(Orders data, XyzDTO reply) {
        log.info("data = {}", data);
        log.info("reply = {}", reply);

        XyzDTO xyzDTO = xyzService.getXyz(reply.getId());

        log.info("xyzDTO = {}", xyzDTO);
    }
    public void handleAccountException(Orders data, Exception reply) {
        log.info(data.getId() + " - " + reply.toString());
    }

    public void approve(Orders data) {
        ordersRepository.findById(data.getId()).get().setStatus(Orders.OrdersStatus.APPROVED);
    }
}
