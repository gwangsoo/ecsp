package com.example.xyz.config;

import com.example.xyz.security.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ApplicationProperties applicationProperties;

//    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
//    private String issuerUri;

    @Profile("!noauth")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        List<String> issuers = applicationProperties.getSecurity().getOauth2().getIssuers();
        issuers.forEach(issuer -> addManager(authenticationManagers, issuer));

        http
            .cors(withDefaults())
            .csrf(csrf ->
                csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    // See https://stackoverflow.com/q/74447118/65681
                    .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
            )
//            .addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class)
            .authorizeHttpRequests(authz ->
                // prettier-ignore
                authz
                    .requestMatchers(mvc.pattern("/api/authenticate")).permitAll()
                    .requestMatchers(mvc.pattern("/api/auth-info")).permitAll()
                    .requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                    .requestMatchers(mvc.pattern("/management/health")).permitAll()
                    .requestMatchers(mvc.pattern("/management/health/**")).permitAll()
                    .requestMatchers(mvc.pattern("/management/info")).permitAll()
                    .requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
                    .requestMatchers(mvc.pattern("/management/**")).hasAuthority(AuthoritiesConstants.ADMIN)
                    // TODO authenticated 로 바꿀것!!
                    .requestMatchers(mvc.pattern("/v3/api-docs/**")).permitAll()
                    .requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
                    .requestMatchers(mvc.pattern("/api/**")).permitAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .authenticationManagerResolver(authenticationManagerResolver))
//            .oauth2Login(withDefaults())
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter())))
//            .oauth2Client()
        ;
        return http.build();
    }

    @Profile("noauth")
    @Bean
    public SecurityFilterChain filterChainElse(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
            .cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz.requestMatchers(mvc.pattern("/**")).permitAll())
        ;
        return http.build();
    }

    private Map<String, AuthenticationManager> authenticationManagers = new HashMap<>();

    private JwtIssuerAuthenticationManagerResolver authenticationManagerResolver =
            new JwtIssuerAuthenticationManagerResolver(authenticationManagers::get);

    private void addManager(Map<String, AuthenticationManager> authenticationManagers, String issuer) {
        JwtAuthenticationProvider authenticationProvider = new JwtAuthenticationProvider(JwtDecoders.fromOidcIssuerLocation(issuer));
        authenticationManagers.put(issuer, authenticationProvider::authenticate);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

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
