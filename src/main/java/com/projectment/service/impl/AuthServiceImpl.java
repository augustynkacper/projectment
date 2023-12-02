package com.projectment.service.impl;

import com.projectment.repository.TokenRepository;
import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.rest.body.response.AuthenticationResponse;
import com.projectment.security.JwtUtil;
import com.projectment.security.Token;
import com.projectment.security.User;
import com.projectment.service.AuthService;
import com.projectment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

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
    public AuthenticationResponse register(RegistrationRequest request) {

        if (userService.getUserByEmail(request.email()) != null) {
            throw new IllegalArgumentException(
                    "User with email " + request.email() + " already exists!");
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

}
