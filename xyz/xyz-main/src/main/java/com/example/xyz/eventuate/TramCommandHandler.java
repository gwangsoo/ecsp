package com.example.xyz.eventuate;

import com.example.xyz.eventuate.command.XyzRegisterCommand;
import com.example.xyz.service.XyzService;
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
    private XyzService xyzService;

    public CommandHandlers getCommandHandlers() {
        return CommandHandlersBuilder
                .fromChannel(commandChannel)
                .onMessage(XyzRegisterCommand.class, this::xyzRegister)
                .build();
    }

    @Transactional
    public Message xyzRegister(CommandMessage<XyzRegisterCommand> cm, PathVariables pvs) {
        log.info("xyzRegister messageId = " + cm.getMessageId());

        try {
            return withSuccess(xyzService.save(cm.getCommand().getXyz()));
        } catch (Exception e) {
            return withFailure();
        }
    }
}
