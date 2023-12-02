package com.projectment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${application.security.secret-key}")
    private String secretKey;

    @Value("${application.security.expiration}")
    private long expiration;

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    public String generateToken(Map<String, Object> claims, User user) {
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .subject(String.valueOf(user.getId()))
                .signWith(getSignKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = DECODER.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
