package com.example.xyz.eventuate;

import com.example.abc.domain.dto.AbcDTO;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.commands.consumer.CommandDispatcherFactory;
import io.eventuate.tram.events.subscriber.DomainEventDispatcher;
import io.eventuate.tram.events.subscriber.DomainEventDispatcherFactory;
import io.eventuate.tram.messaging.consumer.MessageConsumer;
import io.eventuate.tram.spring.commands.consumer.TramCommandConsumerConfiguration;
import io.eventuate.tram.spring.commands.producer.TramCommandProducerConfiguration;
import io.eventuate.tram.spring.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.spring.events.subscriber.TramEventSubscriberConfiguration;
import io.eventuate.tram.spring.jdbckafka.TramJdbcKafkaConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({TramJdbcKafkaConfiguration.class,
        //NoopDuplicateMessageDetector.class, // received_messages
        //TramSpringCloudSleuthIntegrationCommonConfiguration.class, // version 호환안됨
        TramEventsPublisherConfiguration.class,
        TramEventSubscriberConfiguration.class,
        TramCommandProducerConfiguration.class,
        TramCommandConsumerConfiguration.class})
public class TramMessageConfiguration {

    // Event 수신부 ----------------------------------------------------------------------------------------------------
    @Bean
    public DomainEventDispatcher domainEventDispatcher(DomainEventDispatcherFactory domainEventDispatcherFactory, TramEventConsumer target) {
        return domainEventDispatcherFactory.make(XyzTramMessageConfig.eventDispatcherId, target.domainEventHandlers());
    }

    @Bean
    public TramEventConsumer tramEventConsumerTarget() {
        return new TramEventConsumer(AbcDTO.class.getName());
    }

    // Command 수신부 --------------------------------------------------------------------------------------------------
    @Bean
    public CommandDispatcher commandDispatcher(CommandDispatcherFactory commandDispatcherFactory,
                                               TramCommandHandler target) {
        return commandDispatcherFactory.make(XyzTramMessageConfig.commandDispatcherId, target.getCommandHandlers());
    }

    @Bean
    public TramCommandHandler tramCommandHandler() {
        return new TramCommandHandler(XyzTramMessageConfig.commandChannel);
    }

    // Command reply 수신부 --------------------------------------------------------------------------------------------
    @Bean
    public TramReplyReceiver tramMessageConsumer(MessageConsumer messageConsumer) {
        return new TramReplyReceiver(messageConsumer);
    }
}
