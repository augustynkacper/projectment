package com.projectment.service;

import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.rest.body.response.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse register(RegistrationRequest request);

}
