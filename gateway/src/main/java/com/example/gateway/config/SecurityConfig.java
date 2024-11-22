
package com.example.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerLogoutSuccessHandler handler) {
		http
			.cors(x -> x.disable())
			.csrf(x -> x.disable())
//			.headers(Customizer.withDefaults())
			.authorizeExchange(authz ->
				authz
					.pathMatchers("/services/**").authenticated()
					.pathMatchers("/actuator/**").permitAll()
					.anyExchange().authenticated())
			.oauth2Login(withDefaults()) // to redirect to oauth2 login page.;
			.logout(logoutSpec ->
				logoutSpec
					.logoutUrl("/logout")
					.logoutSuccessHandler(handler))
			.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
			;

		return http.build();
	}

	@Value("${base-url}")
	private String baseUrl;

	@Value("${logout-success-url}")
	private String logoutSuccessUrl;

	@Bean
	public ServerLogoutSuccessHandler keycloakLogoutSuccessHandler(ReactiveClientRegistrationRepository repository) {

		OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(repository);

		// 로그아웃이후 페이지
//		oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/main/MainPage");
//		oidcLogoutSuccessHandler.setPostLogoutRedirectUri(baseUrl + "/login/logout");
		oidcLogoutSuccessHandler.setPostLogoutRedirectUri(baseUrl + "/");
		oidcLogoutSuccessHandler.setLogoutSuccessUrl(URI.create(baseUrl+logoutSuccessUrl));

		return oidcLogoutSuccessHandler;
	}

}