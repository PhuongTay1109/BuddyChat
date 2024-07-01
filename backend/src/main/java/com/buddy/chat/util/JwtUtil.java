package com.buddy.chat.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
	private SecretKey key; // the secret key used to sign and verify the JWT.
	private static final long ACCESS_EXPIRATION_TIME = 10000L; //600000L; // 10 mins 
	private static final long REFRESH_EXPIRATION_TIME = 86400000L; // 24 hours

	// constructor
	public JwtUtil(){
        String secreteString = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

	// use UserDetails instead of using our customer Users because
	//  This makes JwtUtil flexible and 
	// compatible with any class implementing UserDetails.
    public String generateAccessToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
    
    public String generateRefreshToken(HashMap<String, Object> claims, String username){
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(key)
                .compact();
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
