package com.projectment.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtUtil {

    public String getProperty(String token, String property) {
        return null;
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return null;
    }

}
