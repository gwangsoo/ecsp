package com.example.bff.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReactiveWebExceptionHandler implements WebExceptionHandler {
    private final ExceptionTranslation translator;
    private final ObjectMapper mapper;

    public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
        if (throwable instanceof ResponseStatusException) {
            Mono<ResponseEntity<Object>> entityMono = this.translator.handleAnyException(throwable, exchange);
            return entityMono.flatMap((entity) -> {
                return this.setHttpResponse(entity, exchange, this.mapper);
            });
        } else {
            return Mono.error(throwable);
        }
    }

    private Mono<Void> setHttpResponse(ResponseEntity<Object> entity, ServerWebExchange exchange, ObjectMapper mapper) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(entity.getStatusCode());
        response.getHeaders().addAll(entity.getHeaders());

        try {
            DataBuffer buffer = response.bufferFactory().wrap(mapper.writeValueAsBytes(entity.getBody()));
            return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
                DataBufferUtils.release(buffer);
            });
        } catch (JsonProcessingException var6) {
            JsonProcessingException ex = var6;
            return Mono.error(ex);
        }
    }
}
