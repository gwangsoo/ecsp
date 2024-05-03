package com.example.bff.config;

import com.example.bff.security.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private final ApplicationProperties applicationProperties;

//    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
//    private String issuerUri;

    @Profile("!noauth")
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        List<String> issuers = applicationProperties.getSecurity().getOauth2().getIssuers();
        issuers.forEach(issuer -> addManager(authenticationManagers, issuer));

        http
            .securityMatcher(
                new NegatedServerWebExchangeMatcher(
                    new OrServerWebExchangeMatcher(pathMatchers("/app/**", "/i18n/**", "/content/**", "/swagger-ui/**"))
                )
            )
            .cors(x -> x.disable())
            .csrf(x -> x.disable())
            .formLogin(x -> x.disable())
            .httpBasic(x -> x.disable())
            .anonymous(x -> x.disable())
            .logout(x -> x.disable())
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .requestCache(cache -> cache.requestCache(NoOpServerRequestCache.getInstance()))
            .headers(withDefaults())
            .authorizeExchange(authz ->
                // prettier-ignore
                authz
                    .pathMatchers("/api/authenticate").permitAll()
                    .pathMatchers("/api/auth-info").permitAll()
                    .pathMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    .pathMatchers("/management/health").permitAll()
                    .pathMatchers("/management/health/**").permitAll()
                    .pathMatchers("/management/info").permitAll()
                    .pathMatchers("/management/prometheus").permitAll()
                    .pathMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                    // TODO authenticated 로 바꿀것!!
                    .pathMatchers("/v3/api-docs/**").permitAll()
                    .pathMatchers("/swagger-ui/**").permitAll()
                    .pathMatchers("/api/**").authenticated()
            )
//            .oauth2Client(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2
                .authenticationManagerResolver(authenticationManagerResolver))
        ;
        return http.build();
    }

    @Profile("noauth")
    @Bean
    public SecurityWebFilterChain filterChainElse(ServerHttpSecurity http) throws Exception {
         http
            .cors(x -> x.disable())
            .csrf(x -> x.disable())
            .formLogin(x -> x.disable())
            .httpBasic(x -> x.disable())
            .anonymous(x -> x.disable())
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .requestCache(cache -> cache.requestCache(NoOpServerRequestCache.getInstance()))
            .authorizeExchange(authz -> authz.anyExchange().permitAll())
        ;
        return http.build();
    }

    private Map<String, ReactiveAuthenticationManager> authenticationManagers = new HashMap<>();

    private JwtIssuerReactiveAuthenticationManagerResolver authenticationManagerResolver =
            new JwtIssuerReactiveAuthenticationManagerResolver(issuer -> Mono.justOrEmpty(authenticationManagers.get(issuer)));

    private void addManager(Map<String, ReactiveAuthenticationManager> authenticationManagers, String issuer) {
        Mono.fromCallable(() -> ReactiveJwtDecoders.fromIssuerLocation(issuer))
                .subscribeOn(Schedulers.boundedElastic())
                .map(JwtReactiveAuthenticationManager::new)
                .doOnNext(authenticationManager -> authenticationManagers.put(issuer, authenticationManager))
                .subscribe();
    }

//    @Bean
//    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
//        return new MvcRequestMatcher.Builder(introspector);
//    }

//    Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthorityConverter());
//        return jwtAuthenticationConverter;
//    }
//
//    /**
//     * Map authorities from "groups" or "roles" claim in ID Token.
//     *
//     * @return a {@link GrantedAuthoritiesMapper} that maps groups from
//     * the IdP to Spring Security Authorities.
//     */
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return authorities -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//            authorities.forEach(authority -> {
//                // Check for OidcUserAuthority because Spring Security 5.2 returns
//                // each scope as a GrantedAuthority, which we don't care about.
//                if (authority instanceof OidcUserAuthority) {
//                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//                    mappedAuthorities.addAll(SecurityUtils.extractAuthorityFromClaims(oidcUserAuthority.getUserInfo().getClaims()));
//                }
//            });
//            return mappedAuthorities;
//        };
//    }
//
//    @Bean
//    JwtDecoder jwtDecoder(ClientRegistrationRepository clientRegistrationRepository, RestTemplateBuilder restTemplateBuilder) {
//        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);
//
//        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(jHipsterProperties.getSecurity().getOauth2().getAudience());
//        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
//        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);
//
//        jwtDecoder.setJwtValidator(withAudience);
//        jwtDecoder.setClaimSetConverter(
//            new CustomClaimConverter(clientRegistrationRepository.findByRegistrationId("oidc"), restTemplateBuilder.build())
//        );
//
//        return jwtDecoder;
//    }
}
