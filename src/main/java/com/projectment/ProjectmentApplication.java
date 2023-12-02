package com.projectment;

import com.projectment.security.JwtUtil;
import com.projectment.security.User;
import com.projectment.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;


@SpringBootApplication
public class ProjectmentApplication {

	private static UserService userService;

	public ProjectmentApplication(UserService userService) {
		ProjectmentApplication.userService = userService;
	}


	public static void main(String[] args) {
		JwtUtil jwtUtil = new JwtUtil();


		var claims = jwtUtil.extractClaims("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMiIsIm5hbWUiOiJKb2huIERvZSIsInJvbGVzIjpbImRyaXZlciIsIm1hbmFnZXIiXSwiaWF0IjoxNzAxNTQ3MTg5LCJleHAiOjE3MDE1NDgzODl9.8KcrxFO0gfEm8qOxqsbqLYS6O-zbl1XTInzYVsDFhsXZpB3RWf6vCbPl2QeZ7O4oCGEBp9Qz7Cw15879lllUSA");

		System.out.println(claims.getSubject());
		System.out.println(claims);

		SpringApplication.run(ProjectmentApplication.class, args);

		// create sample user
		var user = new User("john", "doe", "john@doe.com", "idk");
		user.addAuthorities(Set.of("role1", "role2", "role3"));
		userService.saveUser(user);
	}

}
