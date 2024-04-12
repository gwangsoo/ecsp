//package com.example.orders.service.impl;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.nimbusds.jwt.JWTParser;
//import com.xrfriends.careeasy.careeasybackend.client.KeycloakClient;
//import com.xrfriends.careeasy.careeasybackend.service.KeycloakService;
//import com.xrfriends.careeasy.careeasybackend.service.dto.KeycloakDTO;
//import com.xrfriends.careeasy.careeasybackend.service.mapper.KeycloakMapper;
//import feign.FeignException;
//import feign.jackson.JacksonDecoder;
//import feign.jackson.JacksonEncoder;
//import io.github.resilience4j.circuitbreaker.CircuitBreaker;
//import io.github.resilience4j.feign.FeignDecorator;
//import io.github.resilience4j.feign.FeignDecorators;
//import io.github.resilience4j.feign.Resilience4jFeign;
//import io.github.resilience4j.ratelimiter.RateLimiter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.representations.AccessTokenResponse;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.text.ParseException;
//import java.time.Instant;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//
//@Slf4j
//@Service
//@Transactional
////@RequiredArgsConstructor
//public class KeycloakServiceImpl implements KeycloakService {
//
//    private final Object lock = new Object();
//
//    private final KeycloakMapper keycloakMapper;
//
//    private final KeycloakClient keycloakClientAuth;
//
//    private final KeycloakClient keycloakClient;
//
//    private final String name;
//    private final String uri;
//    private final String realm;
//    private String token;
//
//    private String credential;
//    private AccessTokenResponse accessTokenResponse;
//
//    public KeycloakServiceImpl(KeycloakMapper keycloakMapper,
//                               @Value("${client.keycloak.name}") String name,
//                               @Value("${client.keycloak.uri}") String uri,
//                               @Value("${client.keycloak.realm}") String realm,
//                               @Value("${client.keycloak.token:}") String token,
//                               @Value("${client.keycloak.grantType:}") String grantType,
//                               @Value("${client.keycloak.clientId:}") String clientId,
//                               @Value("${client.keycloak.clientSecret:}") String clientSecret,
//                               @Value("${client.keycloak.username:}") String username,
//                               @Value("${client.keycloak.password:}") String password) {
//        this.keycloakMapper = keycloakMapper;
//        this.name = name;
//        this.uri = uri;
//        this.realm = realm;
//        this.token = token;
//        this.credential = buildCredential(grantType, clientId, clientSecret, username, password);
//
//        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(name);
//        RateLimiter rateLimiter = RateLimiter.ofDefaults(name);
//        FeignDecorator decorator = FeignDecorators.builder()
//                .withCircuitBreaker(circuitBreaker)
//                .withRateLimiter(rateLimiter)
//                .build();
//
//        ObjectMapper objectMapper = new ObjectMapper()
//                .setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE)
//                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
//                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
//                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
//                .registerModule(new JavaTimeModule());
//
//        this.keycloakClient = Resilience4jFeign.builder(decorator)
//                .encoder(new JacksonEncoder(objectMapper))
//                .decoder(new JacksonDecoder(objectMapper))
//                .target(KeycloakClient.class, uri);
//
//        this.keycloakClientAuth = Resilience4jFeign.builder(decorator)
////                .encoder(new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters(new RestTemplate().getMessageConverters()))))
//                .decoder(new JacksonDecoder(objectMapper))
//                .target(KeycloakClient.class, uri);
//    }
//
//    private String buildCredential(String grantType, String clientId, String clientSceret, String userName, String password) {
//        String credential;
//        if(StringUtils.equals(grantType, OAuth2Constants.CLIENT_CREDENTIALS)) {
//            credential = String.format("grant_type=%s&client_id=%s&client_secret=%s", OAuth2Constants.CLIENT_CREDENTIALS, clientId, clientSceret);
//        }
//        else {
//            credential = String.format("grant_type=%s&client_id=%s&username=%s&password=%s", OAuth2Constants.PASSWORD, clientId, userName, password);
//        }
//
//        return credential;
//    }
//
//    @Override
//    public AccessTokenResponse token(String grantType, String clientId, String clientSceret, String userName, String password) {
//        synchronized(lock) {
//            credential = buildCredential(grantType, clientId, clientSceret, userName, password);
//        }
//        return keycloakClientAuth.token(realm, credential);
//    }
//
//    @Override
//    public String getValidToken() {
//        if(StringUtils.isEmpty(token) && accessTokenResponse == null) return null;
//
//        try {
//            if(!StringUtils.isEmpty(token)) {
//                Date date = JWTParser.parse(token).getJWTClaimsSet().getExpirationTime();
//                if(Date.from(Instant.now()).compareTo(date) < 0) return token;
//            }
//            else {
//                Date date = JWTParser.parse(accessTokenResponse.getToken()).getJWTClaimsSet().getExpirationTime();
//                if (Date.from(Instant.now()).compareTo(date) < 0) return accessTokenResponse.getToken();
//            }
//        }
//        catch (ParseException err) {
//            log.error(err.getMessage());
//            return null;
//        }
//
//        return null;
//    }
//
//    @Override
//    public void validCheckAndToken() {
//        String validToken = getValidToken();
//        synchronized(lock) {
//            if(StringUtils.isEmpty(validToken)) {
//                accessTokenResponse = keycloakClientAuth.token(realm, credential);
//                this.token = accessTokenResponse.getToken();
//            }
//        }
//    }
//
//    @Override
//    public String getIdByUsername(String userName) {
//        try {
//            validCheckAndToken();
//            return keycloakClient.getUserByName(token, realm, userName).stream()
//                    .findFirst().orElseThrow()
//                    .getId();
//        }
//        catch (NoSuchElementException err) {
//            log.info("not found username = {}", userName);
//            throw err;
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//    }
//
//    private void clearToken() {
//        synchronized (lock) {
//            token = null;
//            accessTokenResponse = null;
//        }
//    }
//
//    @Override
//    public String createUser(KeycloakDTO keycloakDTO) {
//        try {
//            validCheckAndToken();
//            keycloakClient.createUser(token, realm, keycloakMapper.toKeycloak(keycloakDTO));
//            return getIdByUsername(keycloakDTO.getUsername());
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
////            return null;
//            throw err;
//        }
//    }
//
//    @Override
//    public String updateUser(String id, KeycloakDTO keycloakDTO) {
//        try {
//            validCheckAndToken();
//            keycloakClient.updateUser(token, realm, id, keycloakMapper.toKeycloak(keycloakDTO));
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return id;
//    }
//
//    @Override
//    public String updateUserByUsername(String userName, KeycloakDTO keycloakDTO) {
//        String id;
//        try {
//            validCheckAndToken();
//            id = getIdByUsername(userName);
//            if(id == null) return null;
//            keycloakClient.updateUser(token, realm, id, keycloakMapper.partialUpdate(new UserRepresentation(), keycloakDTO));
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return id;
//    }
//
//    @Override
//    public String deleteUser(String id) {
//        try {
//            validCheckAndToken();
//            keycloakClient.deleteUser(token, realm, id);
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            return null;
//        }
//
//        return id;
//    }
//
//    @Override
//    public String deleteUserByUsername(String userName) {
//        String id;
//        try {
//            validCheckAndToken();
//            id = getIdByUsername(userName);
//            if(id == null) return null;
//            keycloakClient.deleteUser(token, realm, id);
//        }
//        catch (NoSuchElementException err) {
//            return null;
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return id;
//    }
//
//    @Override
//    public KeycloakDTO getUser(String id) {
//        try {
//            validCheckAndToken();
//            return keycloakMapper.toDto(keycloakClient.getUser(token, realm, id));
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//    }
//
//    @Override
//    public KeycloakDTO getUserByUsername(String userName) {
//        try {
//            validCheckAndToken();
//            String id = getIdByUsername(userName);
//            if(id == null) return null;
//            return keycloakMapper.toDto(keycloakClient.getUser(token, realm, id));
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//    }
//
//    @Override
//    public List<KeycloakDTO> getUsers() {
//        try {
//            validCheckAndToken();
//            return keycloakClient.getUsers(token, realm).stream().map(keycloakMapper::toDto).toList();
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            return List.of();
//        }
//    }
//
//    @Override
//    public String logoutUser(String id) {
//        try {
//            validCheckAndToken();
//            keycloakClient.logoutUser(token, realm, id);
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return id;
//    }
//
//    @Override
//    public boolean deleteBruteForceAll() {
//        try {
//            validCheckAndToken();
//            keycloakClient.deleteBruteForceAll(token, realm);
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return true;
//    }
//
//    @Override
//    public Boolean getBruteForceUser(String id) {
//        try {
//            validCheckAndToken();
//            Map response = keycloakClient.getBruteForceUser(token, realm, id);
//            log.info("response = {}", response);
//            return (Boolean) response.get("disabled");
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//    }
//
//    @Override
//    public String deleteBruteForceUser(String id) {
//        try {
//            validCheckAndToken();
//            keycloakClient.deleteBruteForceUser(token, realm, id);
//        }
//        catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//
//        return id;
//    }
//
//    public String resetPasswordByUsername(String userName, CredentialRepresentation representation) throws FeignException {
//        validCheckAndToken();
//        String id = getIdByUsername(userName);
//        if(id == null) return null;
//
//        keycloakClient.resetPassword(token, realm, id, representation);
//
//        return id;
//    }
//
//    @Override
//    public KeycloakDTO getUserByEmail(String email) {
//        try {
//            validCheckAndToken();
//
//            String id =  keycloakClient.getUserByEmail(token, realm, email).stream()
//                    .findFirst().orElseThrow()
//                    .getId();
//
//            if(id == null) return null;
//            return keycloakMapper.toDto(keycloakClient.getUser(token, realm, id));
//        } catch (NoSuchElementException e) {
//            // 없는 경우
//            return null;
//        } catch (FeignException err) {
//            if(err.status() == 401) {
//                clearToken();
//            }
////            err.printStackTrace();
//            throw err;
//        }
//    }
//}
