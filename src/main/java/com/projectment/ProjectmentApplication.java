package com.projectment;

import com.projectment.repository.TokenRepository;
import com.projectment.rest.body.request.RegistrationRequest;
import com.projectment.security.JwtUtil;
import com.projectment.service.AuthService;
import com.projectment.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


@SpringBootApplication
public class ProjectmentApplication {

	private static UserService userService;
	private static TokenRepository tokenRepository;
	private static AuthService authService;

	public ProjectmentApplication(UserService userService, TokenRepository tokenRepository, AuthService authService) {
		ProjectmentApplication.userService = userService;
		ProjectmentApplication.tokenRepository = tokenRepository;
		ProjectmentApplication.authService = authService;
	}


	public static void main(String[] args) {
		JwtUtil jwtUtil = new JwtUtil();

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.setContext(context);

		SpringApplication.run(ProjectmentApplication.class, args);

		var registerRequest = new RegistrationRequest("test", "test", "test@test.com", "test");

		System.out.println(authService.register(registerRequest).jwt());

	}

}
