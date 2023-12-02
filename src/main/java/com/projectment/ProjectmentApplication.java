package com.projectment;

import com.projectment.repository.TokenRepository;
import com.projectment.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectmentApplication {

	private static UserService userService;
	private static TokenRepository tokenRepository;

	public ProjectmentApplication(UserService userService, TokenRepository tokenRepository) {
		ProjectmentApplication.userService = userService;
		ProjectmentApplication.tokenRepository = tokenRepository;
	}


	public static void main(String[] args) {
//		JwtUtil jwtUtil = new JwtUtil();
//
//
//		var claims = jwtUtil.extractClaims("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsIm5hbWUiOiJKb2huIERvZSIsInJvbGVzIjpbImRyaXZlciIsIm1hbmFnZXIiXSwiaWF0IjoxNzAxNTQ3MTg5LCJleHAiOjE3MDE1NTgzODl9.gJa6mpgxc8FSOZ598vylE-n9mvKImaqJiiB7EpxeSiIkKB921AHEPCeMgPH9lKnGmy15NrqCulARAWCaAhABYQ");
////
//		System.out.println(claims.getSubject());
//		System.out.println(claims);

		SpringApplication.run(ProjectmentApplication.class, args);

		// create sample user
//		var user = new User("john", "doe", "john@doe.com", "idk");
//		user.addAuthorities(Set.of("role1", "role2", "role3"));
//		userService.saveUser(user);
//
//		var token1 = new Token("asdf", 12, 3412);
//		var token2 = new Token("asadfsdf", 12, 3412);
//		var token3 = new Token("asvxvxdffv", 13, 3412);
//
//		tokenRepository.saveAll(List.of(token1, token2, token3));
//
//		System.out.println(tokenRepository.findAllBySubjectId(12));

	}

}
