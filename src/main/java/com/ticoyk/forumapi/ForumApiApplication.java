package com.ticoyk.forumapi;

import com.ticoyk.forumapi.app.auth.domain.Role;
import com.ticoyk.forumapi.app.auth.domain.User;
import com.ticoyk.forumapi.app.auth.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
public class ForumApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApiApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveUser(new User(null, "Admin", "admin", "password", new HashSet<>()));
			userService.saveUser(new User(null, "User", "user", "password", new HashSet<>()));
			userService.addRoleToUser("admin", "ROLE_ADMIN");
			userService.addRoleToUser("user", "ROLE_USER");
		};
	}

}
