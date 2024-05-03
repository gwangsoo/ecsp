package com.example.bff.config;

import com.example.bff.exception.ExceptionTranslator;
import com.example.bff.exception.ReactiveWebExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebExceptionHandler;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfigurer implements WebFluxConfigurer {

    private final Environment env;

    private final ApplicationProperties applicationProperties;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = applicationProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    @Order(-2) // The handler must have precedence over WebFluxResponseStatusExceptionHandler and Spring Boot's ErrorWebExceptionHandler
    public WebExceptionHandler problemExceptionHandler(ObjectMapper mapper, ExceptionTranslator problemHandling) {
        return new ReactiveWebExceptionHandler(problemHandling, mapper);
    }
}
