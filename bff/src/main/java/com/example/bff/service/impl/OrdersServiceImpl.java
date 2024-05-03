package com.example.bff.service.impl;

import com.example.bff.client.OrdersServiceClient;
import com.example.bff.security.SecurityUtils;
import com.example.bff.service.OrdersService;
import com.example.orders.domain.dto.OrdersDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.FeignException;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorator;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
//@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final OrdersServiceClient ordersServiceClient;

    private final String name;
    private final String uri;

    public OrdersServiceImpl(@Value("${client.ordersService.name}") String name,
                             @Value("${client.ordersService.uri}") String uri) {

        this.name = name;
        this.uri = uri;

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(name);
        RateLimiter rateLimiter = RateLimiter.ofDefaults(name);
        FeignDecorator decorator = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .withRateLimiter(rateLimiter)
                .build();

        ObjectMapper objectMapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .registerModule(new JavaTimeModule());

        this.ordersServiceClient = Resilience4jFeign.builder(decorator)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(OrdersServiceClient.class, uri);
    }

    @Override
    public OrdersDTO createOrders(OrdersDTO ordersDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return ordersServiceClient.createOrders(token, ordersDTO);
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
//            err.printStackTrace();
//            return null;
            throw err;
        }
    }

    @Override
    public OrdersDTO partialUpdateOrders(String id, OrdersDTO ordersDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return ordersServiceClient.partialUpdateOrders(token, id, ordersDTO);
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
//            err.printStackTrace();
//            return null;
            throw err;
        }
    }

    @Override
    public void deleteOrders(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            ordersServiceClient.deleteOrders(token, id);
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
//            err.printStackTrace();
//            return null;
            throw err;
        }
    }

    @Override
    public List<OrdersDTO> getAllOrders(OrdersDTO.OrdersStatus status) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return ordersServiceClient.getAllOrders(token, status);
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
            err.printStackTrace();
            throw err;
        }
    }

    @Override
    public OrdersDTO getOrders(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return ordersServiceClient.getOrders(token, id);
        }
        catch (NoSuchElementException err) {
            log.info("not found id = {}", id);
            throw err;
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
//            err.printStackTrace();
            throw err;
        }
    }
}
