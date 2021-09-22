package com.ticoyk.sqstudent.api;

import com.ticoyk.sqstudent.api.auth.user.attributes.Authority;
import com.ticoyk.sqstudent.api.auth.user.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ticoyk.sqstudent.api.auth.user.UserService;

@SpringBootApplication
public class SQStudentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SQStudentApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			User appUser = new User();
			appUser.setUsername("user@email.com");
			appUser.setName("User");
			appUser.setPassword("password");
			appUser.setAuthority(Authority.APPUSER);

			User admin = new User();
			admin.setUsername("admin");
			admin.setName("Admin");
			admin.setPassword("password");
			admin.setAuthority(Authority.ADMIN);

			userService.saveUser(appUser);
			userService.saveUser(admin);
		};
	}

}
