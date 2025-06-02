package in.chillforce.ai.guruplusplus.service.implementation.security;

import java.util.List;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReactiveAuthenticationManagerImpl implements ReactiveAuthenticationManager{

	private final JwtTokenService tokenService;
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		
		 log.info("Reactive Authentication Manager Invokee");
		 
		 String authToken = authentication.getCredentials().toString();

	        return tokenService.validateToken(authToken)
	                .map(claims -> {
	                	
	                    String userId = claims.getSubject();
	                    String role = claims.get("role", String.class);

	                    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

	                    return new UsernamePasswordAuthenticationToken(userId, authToken, authorities);
	                });
	}

}
