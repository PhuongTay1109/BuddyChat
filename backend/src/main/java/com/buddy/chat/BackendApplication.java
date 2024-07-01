package com.buddy.chat;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.buddy.chat.model.Role;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.RoleRepository;
import com.buddy.chat.repository.UserRepository;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role adminRole;
		Role userRole;
		if (roleRepository.findByAuthority("ROLE_ADMIN") == null) {
			adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
			System.out.println(adminRole);
		}

		if (roleRepository.findByAuthority("ROLE_USER") == null) {
			userRole = roleRepository.save(new Role("ROLE_USER"));
			System.out.println(userRole);
		}

		if (userRepository.findByUsername("admin") == null) {
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByAuthority("ROLE_ADMIN"));
			User admin = User.builder()
					.username("admin")
					.password("123")
					.email("admin@gmail")
					.firstName("Im")
					.lastName("Admin")
					.profilePicture("")
					.isEnabled(true)
					.roles(roles)
					.build();
			userRepository.save(admin);
		}
		if (userRepository.findByUserId(2) == null) {
			for (int i = 0; i < 5; ++i) {
				Set<Role> roles = new HashSet<>();
				roles.add(roleRepository.findByAuthority("ROLE_USER"));
				User user = User.builder()
						.username("user")
						.password("123")
						.email("user@gmail")
						.firstName("Im")
						.lastName("User")
						.profilePicture("")
						.isEnabled(true)
						.roles(roles)
						.build();
				userRepository.save(user);
			}
		}
	}
}
