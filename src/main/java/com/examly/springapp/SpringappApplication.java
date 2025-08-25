package com.examly.springapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.examly.springapp.enums.Role;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

@SpringBootApplication
public class SpringappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringappApplication.class, args);
	}

	@Bean
	public CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			String adminEmail = "admin@vegansnacks.com";
			if (userRepository.findByEmail(adminEmail).isEmpty()) {
				// Role adminRole = roleRepository.findByName("ADMIN")
				// 	.orElseGet(() -> roleRepository.save(new Role("ADMIN")));

				User admin = new User();
				admin.setUserName("Admin");
				admin.setEmail(adminEmail);
				admin.setPassword(passwordEncoder.encode("Admin@123")); // Use a strong password
				admin.setRole(Role.ADMIN);
				// admin.setActive(true);
				userRepository.save(admin);

				System.out.println("✅ Admin created: " + adminEmail);
			}
		};
	}


}
