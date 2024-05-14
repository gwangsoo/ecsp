package com.example.abc.eventuate;

import com.example.abc.eventuate.command.AbcRegisterCommand;
import com.example.abc.eventuate.command.ConfirmAbcCommand;
import com.example.ecsp.common.jpa.TenantContext;
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
                .onMessage(AbcRegisterCommand.class, this::abcRegister)
                .onMessage(ConfirmAbcCommand.class, this::confirmAbc)
                .build();
    }

    @Transactional
    public Message abcRegister(CommandMessage<AbcRegisterCommand> cm, PathVariables pvs) {
        log.info("abcRegister messageId = " + cm.getMessageId());
        log.info("abcRegister tenant = " + cm.getMessage().getHeader("tenant"));

        try {
            // tenant 설정
            TenantContext.setCurrentTenant(cm.getMessage().getHeader("tenant").orElse(null));

            return withSuccess(eventuateHandlerService.abcRegister(cm.getCommand().getAbc()));
        } catch (Exception e) {
            return withFailure();
        }
    }

    @Transactional
    public Message confirmAbc(CommandMessage<ConfirmAbcCommand> cm, PathVariables pvs) {
        log.info("confirmAbc messageId = " + cm.getMessageId());
        log.info("confirmAbc tenant = " + cm.getMessage().getHeader("tenant"));

        try {
            // tenant 설정
            TenantContext.setCurrentTenant(cm.getMessage().getHeader("tenant").orElse(null));

            eventuateHandlerService.confirmAbc(cm.getCommand().getAbc());
            return withSuccess();
        } catch (Exception e) {
            return withFailure(e);
        }
    }
}
