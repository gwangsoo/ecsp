
package com.example.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;

import java.net.URI;

@Configuration
//@EnableWebFluxSecurity
public class SecurityConfig {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerLogoutSuccessHandler handler) {
		http
			.csrf()
//				.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
				.disable() // TODO 보안강화
//		.and()
//			.addFilterAt(new CookieCsrfFilter(), SecurityWebFiltersOrder.REACTOR_CONTEXT)
//			.addFilterBefore(corsWebFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT)
//			.addFilterAt(new SpaWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
//			.exceptionHandling()
//				.accessDeniedHandler(problemSupport)
//				.authenticationEntryPoint(problemSupport)
//		.and()
			.headers()
//				.contentSecurityPolicy(applicationProperties.getSecurity().getContentSecurityPolicy())
//			.and()
				.referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
//			.and()
//				.permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
			.and()
//				.frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.DENY)
		.and()
			.authorizeExchange()
//				.pathMatchers("/").permitAll()
//				.pathMatchers("/actuator/**").permitAll()
//				.anyExchange().authenticated()
				.pathMatchers("/services/backend/api/**").authenticated()
				.pathMatchers("/services/alarm/api/**").authenticated()
				.pathMatchers("/actuator/**").permitAll()
				.anyExchange().authenticated()
		.and()
			.oauth2Login() // to redirect to oauth2 login page.;
		.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(handler)
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