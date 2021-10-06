package com.ticoyk.sqstudent.api;

import com.ticoyk.sqstudent.api.auth.user.User;
import com.ticoyk.sqstudent.api.auth.user.UserService;
import com.ticoyk.sqstudent.api.auth.user.attributes.Authority;
import com.ticoyk.sqstudent.api.auth.user.dto.UserDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SQStudentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SQStudentApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			UserDTO appUser = new UserDTO();
			appUser.setUsername("user@email.com");
			appUser.setName("User");
			appUser.setPassword("password");

			UserDTO admin = new UserDTO();
			admin.setUsername("admin@email.com");
			admin.setName("Admin");
			admin.setPassword("password");

			userService.saveUser(appUser);
			User user = userService.saveUser(admin);
			user.setAuthority(Authority.ADMIN);
			userService.saveUser(user);
		};
	}

}
