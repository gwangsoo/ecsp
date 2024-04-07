package com.example.xyz.eventuate;

import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
public class TramReplyReceiver {

    public TramReplyReceiver(MessageConsumer messageConsumer) {
        messageConsumer.subscribe(XyzTramMessageConfig.commandDispatcherId,
                Collections.singleton(XyzTramMessageConfig.replyChannel),
                this::handleMessage);
    }

    private void handleMessage(Message message) {
        log.info("message = " + message);
        log.info("message.payload = " + message.getPayload());
        String commandReplyType = message.getHeader(ReplyMessageHeaders.REPLY_TYPE).orElseThrow(() -> new RuntimeException("commandReply_type is null or empty"));
        String replyOutcomeType = message.getHeader(ReplyMessageHeaders.REPLY_OUTCOME).orElseThrow(() -> new RuntimeException(ReplyMessageHeaders.REPLY_OUTCOME + " is null or empty"));

        log.info("commandReplyType = " + commandReplyType);
        log.info("replyOutcomeType = " + replyOutcomeType);
    }
}
