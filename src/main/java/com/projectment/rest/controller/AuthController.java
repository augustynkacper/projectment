package com.projectment.rest.controller;

import com.projectment.rest.body.request.LoginRequest;
import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.rest.body.response.AuthenticationResponse;
import com.projectment.service.AuthService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // TODO cleaner way to return 409 reponse
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest requestBody) {
        try{
            return ResponseEntity.ok(authService.register(requestBody));
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }

    // TODO cleaner way to return 403 response
    @GetMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest requestBody) throws AccessDeniedException {
        try {
            return ResponseEntity.ok(authService.login(requestBody));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(403));
        }
    }

}
