package com.projectment.service.impl;

import com.projectment.repository.TokenRepository;
import com.projectment.rest.body.request.LoginRequest;
import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.rest.body.response.AuthenticationResponse;
import com.projectment.security.JwtUtil;
import com.projectment.security.Token;
import com.projectment.security.User;
import com.projectment.service.AuthService;
import com.projectment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(
            UserService userService,
            TokenRepository tokenRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthenticationResponse register(RegistrationRequest request) throws IllegalArgumentException{

        logger.info("Registration request with email {}", request.email());

        if (userService.getUserByEmail(request.email()) != null) {
            throw new IllegalArgumentException(
                    "User with email " + request.email() + " already exists");
        }

        var user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        userService.saveUser(user);

        var jwt = jwtUtil.generateToken(
                Collections.emptyMap(),
                user
        );
        var token = new Token(jwt, user.getId(), jwtUtil.extractExpirationTime(jwt));

        tokenRepository.save(token);

        logger.info("Registered new user: {}", user);

        return new AuthenticationResponse(jwt);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) throws org.springframework.security.access.AccessDeniedException {

        logger.info("User with email {} requested logging in", request.email());

        var user = userService.getUserByEmail(request.email());

        if (Objects.isNull(user)) {
            throw new AccessDeniedException(
                    String.format("Couldn't find an user with email %s", request.email()));
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new AccessDeniedException(
                    String.format("User with email %s entered wrong password", request.email()));
        }

        var jwt = jwtUtil.generateToken(
                Map.of("roles", user.getAuthorities()),
                user
        );

        var token  = new Token(jwt, user.getId(), jwtUtil.extractExpirationTime(jwt));
        tokenRepository.save(token);

        logger.info("User with email {} logged in", user.getEmail());
        return new AuthenticationResponse(jwt);
    }

}
