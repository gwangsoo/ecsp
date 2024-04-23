package com.example.orders.eventuate;

import com.example.orders.domain.dto.OrdersDTO;
import com.example.orders.domain.entity.Orders;
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
    private com.example.orders.service.OrdersService ordersService;

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

        try {
            return withSuccess(ordersService.save(cm.getCommand().getOrders()));
        } catch (Exception e) {
            return withFailure();
        }
    }

    @Transactional
    public Message confirmOrders(CommandMessage<OrdersCancelCommand> cm, PathVariables pvs) {
        log.info("confirmOrders messageId = " + cm.getMessageId());

        try {
            OrdersDTO ordersDto = ordersService.findOne(cm.getCommand().getOrders().getId()).orElseThrow();
            if(ordersDto.getStatus() == OrdersDTO.OrdersStatus.APPROVED) {
                throw new BadRequestAlertException("유효한 데이터가 아님", OrdersDTO.class.getName(), "idnull");
            }
            return withSuccess();
        } catch (Exception e) {
            return withFailure(e);
        }
    }
}
