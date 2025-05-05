package com.example.Healthcare_app.util;

import java.sql.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret.key}")
	private String secret;
	
	//private final String secret = "YourSuperSecretKey123"; // Must be at least 256 bits for HS256/HS512
    private final long expiration = 1000 * 60 * 60 * 10; // 10 hours

    public String generateToken(String username,String role) {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(expiration))
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    

}
