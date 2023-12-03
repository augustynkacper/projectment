package com.projectment.service;

import com.projectment.rest.body.request.LoginRequest;
import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.rest.body.response.AuthenticationResponse;
import org.springframework.security.access.AccessDeniedException;

public interface AuthService {

    AuthenticationResponse register(RegistrationRequest request);

    AuthenticationResponse login(LoginRequest request) throws AccessDeniedException;

}
