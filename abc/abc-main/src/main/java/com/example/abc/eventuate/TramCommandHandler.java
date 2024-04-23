package com.example.abc.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import com.example.abc.eventuate.command.AbcRegisterCommand;
import com.example.abc.eventuate.command.ConfirmAbcCommand;
import com.example.abc.exception.BadRequestAlertException;
import com.example.abc.service.AbcService;
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
    private AbcService abcService;

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

        try {
            return withSuccess(abcService.save(cm.getCommand().getAbc()));
        } catch (Exception e) {
            return withFailure();
        }
    }

    @Transactional
    public Message confirmAbc(CommandMessage<ConfirmAbcCommand> cm, PathVariables pvs) {
        log.info("confirmAbc messageId = " + cm.getMessageId());

        try {
            AbcDTO abcDto = abcService.findOne(cm.getCommand().getAbc().getId()).orElseThrow();
            if(abcDto.getStatus() != AbcDTO.AbcStatus.OPEN) {
                throw new BadRequestAlertException("유효한 데이터가 아님", AbcDTO.class.getName(), "idnull");
            }
            return withSuccess();
        } catch (Exception e) {
            return withFailure(e);
        }
    }
}
