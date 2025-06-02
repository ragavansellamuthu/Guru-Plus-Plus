package in.chillforce.ai.guruplusplus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final ServerSecurityContextRepository serverSecurityContextRepository;
	
	private final ReactiveAuthenticationManager reactiveAuthenticationManager;
// hello 	
	@Bean
	SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
		 return http
		            .csrf(ServerHttpSecurity.CsrfSpec::disable)
		            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
		            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
		            .authenticationManager(reactiveAuthenticationManager)
		            .securityContextRepository(serverSecurityContextRepository)
		            .authorizeExchange(exchanges -> exchanges
		                .pathMatchers("/token/**").permitAll()
		                .anyExchange().authenticated()
		            )
		            .build();
	}
}
