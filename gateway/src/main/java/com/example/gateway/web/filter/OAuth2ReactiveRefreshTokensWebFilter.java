package com.example.gateway.web.filter;

import com.nimbusds.jose.util.Base64URL;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * Refresh oauth2 tokens based on TokenRelayGatewayFilterFactory.
 */
@Component
@RequiredArgsConstructor
public class OAuth2ReactiveRefreshTokensWebFilter implements WebFilter {

    private final ReactiveOAuth2AuthorizedClientManager clientManager;

    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ServerWebExchangeMatchers.pathMatchers("/services/**")
            .matches(exchange)
            .filter(matchResult -> matchResult.isMatch() == true)
            .flatMap(matchResult -> exchange
                .getPrincipal()
                .filter(principal -> principal instanceof OAuth2AuthenticationToken)
                .cast(OAuth2AuthenticationToken.class)
                .flatMap(authentication -> authorizedClient(exchange, authentication)))
            .thenReturn(exchange)
            .flatMap(chain::filter);
    }

    private Mono<OAuth2AuthorizedClient> authorizedClient(ServerWebExchange exchange, OAuth2AuthenticationToken oauth2Authentication) {
        String clientRegistrationId = oauth2Authentication.getAuthorizedClientRegistrationId();
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest
            .withClientRegistrationId(clientRegistrationId)
            .principal(oauth2Authentication)
            .attribute(ServerWebExchange.class.getName(), exchange)
            .build();
        if (clientManager == null) {
            return Mono.error(
                new IllegalStateException(
                    "No ReactiveOAuth2AuthorizedClientManager bean was found. Did you include the " +
                    "org.springframework.boot:spring-boot-starter-oauth2-client dependency?"
                )
            );
        }

        DefaultOidcUser defaultOidcUser = (request.getPrincipal().getPrincipal() instanceof DefaultOidcUser ? (DefaultOidcUser)request.getPrincipal().getPrincipal() : null);
        String sid = (defaultOidcUser != null ? defaultOidcUser.getAttribute("sid") : "");

        return clientManager.authorize(request)
                .onErrorMap(ClientAuthorizationException.class, (e) -> new ClientAuthorizationRequiredException(request.getClientRegistrationId()))
                .map(authorizedClient -> {
                    return compareSessionId(authorizedClient, sid);
                });
    }

    private OAuth2AuthorizedClient compareSessionId(OAuth2AuthorizedClient authorizedClient, String sid) {
        if(StringUtils.isEmpty(sid)) return authorizedClient;

        JWT jwt = null;
        try {
            jwt = JWTParser.parse(authorizedClient.getAccessToken().getTokenValue());
        } catch (ParseException err) {
            return authorizedClient;
        }

        for(Base64URL base64URL : jwt.getParsedParts()) {
            if(base64URL.decodeToString().contains(sid)) {
                return authorizedClient;
            }
        }

        throw new ClientAuthorizationRequiredException(authorizedClient.getClientRegistration().getRegistrationId());
    }
}
