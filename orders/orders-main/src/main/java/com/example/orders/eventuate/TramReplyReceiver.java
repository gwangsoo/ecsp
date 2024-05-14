package com.example.orders.eventuate;

import com.example.ecsp.common.jpa.TenantContext;
import io.eventuate.tram.commands.common.ReplyMessageHeaders;
import io.eventuate.tram.messaging.common.Message;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

@Slf4j
public class TramReplyReceiver {

    public TramReplyReceiver(MessageConsumer messageConsumer) {
        messageConsumer.subscribe(OrdersTramMessageConfig.commandDispatcherId,
                Collections.singleton(OrdersTramMessageConfig.replyChannel),
                this::handleMessage);
    }

    @Autowired
    private EventuateHandlerService eventuateHandlerService;

    private void handleMessage(Message message) {
        log.info("message = " + message);
        log.info("message.payload = " + message.getPayload());
        String commandReplyType = message.getHeader(ReplyMessageHeaders.REPLY_TYPE).orElseThrow(() -> new RuntimeException("commandReply_type is null or empty"));
        String replyOutcomeType = message.getHeader(ReplyMessageHeaders.REPLY_OUTCOME).orElseThrow(() -> new RuntimeException(ReplyMessageHeaders.REPLY_OUTCOME + " is null or empty"));

        log.info("commandReplyType = " + commandReplyType);
        log.info("replyOutcomeType = " + replyOutcomeType);

        // 테넌트를 설정하고 required_new 로 transaction 설정한 java서비스를 호출 해야 함.
        TenantContext.setCurrentTenant(message.getHeader("tenant").orElse(null));

    }
}
