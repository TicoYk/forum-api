package com.ticoyk.forumapi;

import com.ticoyk.forumapi.auth.user.attributes.Authority;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ticoyk.forumapi.auth.user.User;
import com.ticoyk.forumapi.auth.user.UserService;

@SpringBootApplication
public class ForumApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApiApplication.class, args);
	}



	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			User appUser = new User();
			appUser.setUsername("user");
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
