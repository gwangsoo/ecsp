package com.example.bff.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Hooks;

@Configuration
@Profile("!" + Constants.SPRING_PROFILE_PRODUCTION)
public class ReactorConfiguration {

    public ReactorConfiguration() {
        Hooks.onOperatorDebug();
    }
}