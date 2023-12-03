package com.projectment.security;

import com.projectment.repository.TokenRepository;
import com.projectment.service.UserService;
import com.projectment.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final TokenRepository tokenRepository;

    public AuthenticationFilter(JwtUtil jwtUtil, UserServiceImpl userService, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authorizationHeader.substring(7);
        String userId = jwtUtil.extractUserId(jwt);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.getUserById(userId);
            Token token = tokenRepository.findByValue(jwt).orElse(null);

            if (token != null && !token.isExpired() && !token.isRevoked()){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
