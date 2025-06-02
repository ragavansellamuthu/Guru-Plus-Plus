package in.chillforce.ai.guruplusplus.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import in.chillforce.ai.guruplusplus.service.implementation.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DemoController {
	
	private final JwtTokenService tokenService;
	
	@PostMapping("/token")
	public Mono<String> token() {
		return tokenService.generateAccessToken(101L, "ROLE_ADMIN");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/hello")
	public Mono<String> hello(@AuthenticationPrincipal String userId) {
		return Mono.just("Authenticated User ID : " + userId);
	}
}
