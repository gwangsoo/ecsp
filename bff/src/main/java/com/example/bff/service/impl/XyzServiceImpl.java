package com.example.bff.service.impl;

import com.example.bff.client.XyzServiceClient;
import com.example.bff.security.SecurityUtils;
import com.example.bff.service.XyzService;
import com.example.xyz.domain.dto.XyzDTO;
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
public class XyzServiceImpl implements XyzService {

    private final XyzServiceClient xyzServiceClient;

    private final String name;
    private final String uri;

    public XyzServiceImpl(@Value("${client.xyzService.name}") String name,
                          @Value("${client.xyzService.uri}") String uri) {

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

        this.xyzServiceClient = Resilience4jFeign.builder(decorator)
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(XyzServiceClient.class, uri);
    }

    @Override
    public XyzDTO createXyz(XyzDTO xyzDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return xyzServiceClient.createXyz(token, xyzDTO);
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
    public XyzDTO partialUpdateXyz(String id, XyzDTO xyzDTO) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return xyzServiceClient.partialUpdateXyz(token, id, xyzDTO);
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
    public void deleteXyz(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            xyzServiceClient.deleteXyz(token, id);
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
    public List<XyzDTO> getAllXyzs() {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return xyzServiceClient.getAllXyzs(token);
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
    public XyzDTO getXyz(String id) {
        try {
            String token = SecurityUtils.getCurrentUserToken().block();
            return xyzServiceClient.getXyz(token, id);
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
