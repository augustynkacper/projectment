package com.projectment.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

   // @Value("${application.security.secret-key}")
    private String secretKey = "UxCSli43hlRTUV4AVAr0do+TCvlhaSP0082YP1GKmG3fyJ4DilrYEpVooqlKHazlRz/paDR9ROCytG1D7RE86hfBLL+RIWGwdlh0Wonhhp3a1a/wIZZ0moda3QycXM9H8Qk4MtXwcInGIY3AqCnTQW8gtYDxKQ0Hz13LavGU+SxD7BWUUVb0Oy/rh62QznrZvAlveqOYsVEAj4/ZFfobW9PfpFN6og9yXYHJwnRh8EWlzKcQ7ON6XHI90x5jc8FaEKpRMzlDuJ8/zT/ojyRrpsVf28ZgmkVBhr/Y/InR5fFJwNiFmkeNNhWmHtqCsP9niKzgF2biETtwjRjtGGnvUtV6+pmAicJmWo2u5aMlfVE=";

    //@Value("${application.security.expiration}")
    private long expiration = 1200000;

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
