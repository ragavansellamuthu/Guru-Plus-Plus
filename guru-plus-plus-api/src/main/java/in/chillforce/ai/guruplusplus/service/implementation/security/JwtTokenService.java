package in.chillforce.ai.guruplusplus.service.implementation.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class JwtTokenService {

	private byte[] getKeyBytes() {
        return Decoders.BASE64.decode("G2Xq5E1h9hV9YxB1yKHY3P5KQ+3E5fiR1DdPLeV9WLM=");
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(getKeyBytes());
    }

    private SecretKey getSecretKey() {
        return new SecretKeySpec(getKeyBytes(), "HmacSHA256");
    }
    
	public Mono<String> generateAccessToken(Long userId, String role) {
	    return Mono.fromSupplier(() -> Jwts.builder()
	            .subject(userId.toString())
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + 5000000))
	            .claim("role", role)
	            .signWith(getKey())
	            .compact());
	}
	
	public Mono<Claims> validateToken(String token){
		return Mono.fromCallable(() -> Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload())
                .subscribeOn(Schedulers.boundedElastic());
	} 

	
}
