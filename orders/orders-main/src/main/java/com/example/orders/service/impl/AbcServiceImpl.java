package com.example.orders.service.impl;

import com.example.abc.domain.dto.AbcDTO;
import com.example.orders.client.AbcServiceClient;
import com.example.orders.security.SecurityUtils;
import com.example.orders.service.AbcService;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
//@RequiredArgsConstructor
public class AbcServiceImpl implements AbcService {

    private final AbcServiceClient abcServiceClient;

    private final String name;
    private final String uri;

    public AbcServiceImpl(@Value("${client.abcService.name}") String name,
                          @Value("${client.abcService.uri}") String uri) {

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

        this.abcServiceClient = Resilience4jFeign.builder(decorator)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(AbcServiceClient.class, uri);
    }

    @Override
    public AbcDTO createAbc(AbcDTO abcDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().orElse(null);
            return abcServiceClient.createAbc(token, abcDTO);
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
    public AbcDTO partialUpdateAbc(String id, AbcDTO abcDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().orElse(null);
            return abcServiceClient.partialUpdateAbc(token, id, abcDTO);
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
    public void deleteAbc(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().orElse(null);
            abcServiceClient.deleteAbc(token, id);
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
    public List<AbcDTO> getAllAbcs() {
        try {
            String token = SecurityUtils.getCurrentUserToken().orElse(null);
            return abcServiceClient.getAllAbcs(token);
        }
        catch (FeignException err) {
            if(err.status() == 401) {
//                clearToken();
            }
//            err.printStackTrace();
            throw err;
        }
    }

    @Override
    public AbcDTO getAbc(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().orElse(null);
            return abcServiceClient.getAbc(token, id);
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
