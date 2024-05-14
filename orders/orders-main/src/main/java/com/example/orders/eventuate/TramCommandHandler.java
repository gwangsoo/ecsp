package com.example.orders.eventuate;

import com.example.ecsp.common.jpa.TenantContext;
import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.eventuate.command.OrdersCancelCommand;
import com.example.orders.eventuate.command.OrdersReceptionCommand;
import com.example.orders.exception.BadRequestAlertException;
import io.eventuate.tram.commands.consumer.CommandHandlers;
import io.eventuate.tram.commands.consumer.CommandHandlersBuilder;
import io.eventuate.tram.commands.consumer.CommandMessage;
import io.eventuate.tram.commands.consumer.PathVariables;
import io.eventuate.tram.messaging.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withFailure;
import static io.eventuate.tram.commands.consumer.CommandHandlerReplyBuilder.withSuccess;

@Slf4j
public class TramCommandHandler {

    private String commandChannel;

    public TramCommandHandler(String commandChannel) {
        this.commandChannel = commandChannel;
    }

    @Autowired
    private EventuateHandlerService eventuateHandlerService;

    public CommandHandlers getCommandHandlers() {
        return CommandHandlersBuilder
                .fromChannel(commandChannel)
                .onMessage(OrdersReceptionCommand.class, this::ordersRegister)
                .onMessage(OrdersCancelCommand.class, this::confirmOrders)
                .build();
    }

    @Transactional
    public Message ordersRegister(CommandMessage<OrdersReceptionCommand> cm, PathVariables pvs) {

        log.info("ordersRegister messageId = " + cm.getMessageId());
        log.info("ordersRegister tenant = " + cm.getMessage().getHeader("tenant"));

        try {
            // tenant 설정
            TenantContext.setCurrentTenant(cm.getMessage().getHeader("tenant").orElse(null));

            return withSuccess(eventuateHandlerService.orderRegister(cm.getCommand().getOrders()));
        } catch (Exception e) {
            return withFailure();
        }
    }

    @Transactional
    public Message confirmOrders(CommandMessage<OrdersCancelCommand> cm, PathVariables pvs) {
        log.info("confirmOrders messageId = " + cm.getMessageId());
        log.info("confirmOrders tenant = " + cm.getMessage().getHeader("tenant"));

        try {
            // tenant 설정
            TenantContext.setCurrentTenant(cm.getMessage().getHeader("tenant").orElse(null));

            eventuateHandlerService.confirmOrders(cm.getCommand().getOrders());

            return withSuccess();
        } catch (Exception e) {
            return withFailure(e);
        }
    }
}
