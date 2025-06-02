package in.chillforce.ai.guruplusplus.service.implementation.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

	private final ReactiveAuthenticationManagerImpl reactiveAuthenticationManager;
	
	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		return Mono.error(new UnsupportedOperationException("Save operation is not supported"));
	}

	 @Override
	    public Mono<SecurityContext> load(ServerWebExchange swe) {
		    
		 log.info("Security Context Holder Invoked");
		 
	        String authHeader = swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            String authToken = authHeader.substring(7);
	            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
	            return reactiveAuthenticationManager.authenticate(auth)
	                    .map(SecurityContextImpl::new);
	        } else {
	            return Mono.empty();
	        }
	    }

}
