package com.buddy.chat.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	private SecretKey key; // the secret key used to sign and verify the JWT.

	private static final long ACCESS_EXPIRATION_TIME = 30000L; // 30s - testing
//	private static final long ACCESS_EXPIRATION_TIME = 600000L; // 10 mins 
	private static final long REFRESH_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 365 * 10; // 10 years

    @Value("${jwt.secret}")
    private String secretString;
    
    // Use @PostConstruct to initialize key after the bean is constructed and secretString is injected.
    // Initialize SecretKey after secretString is injected
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

	// use UserDetails instead of using our customer Users because
	//  This makes JwtUtil flexible and 
	// compatible with any class implementing UserDetails.
    public String generateAccessToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .claim("roles", determinRoles(userDetails))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    
    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .claim("roles", determinRoles(userDetails))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    
    private String determinRoles(UserDetails userDetails) {
    	return userDetails.getAuthorities().stream()
    	            .map(GrantedAuthority::getAuthority)
    	            .collect(Collectors.joining(","));
    }

    // "claims" are attributes or information embedded within the token
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
    	try {
    		return claimsTFunction.apply(Jwts.parser()
		    		   .verifyWith(key)
		    	       .build()
		    		   .parseSignedClaims(token)
		    		   .getPayload());
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw e;
        }	
    }
    
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
    
    public boolean isTokenValid(String token){
        return !isTokenExpired(token);
    }
}