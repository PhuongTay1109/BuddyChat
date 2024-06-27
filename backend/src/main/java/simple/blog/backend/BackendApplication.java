package simple.blog.backend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import simple.blog.backend.model.Role;
import simple.blog.backend.model.User;
import simple.blog.backend.repository.RoleRepository;
import simple.blog.backend.repository.UserRepository;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

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

		if (userRepository.findByUsername("admin") != null) {
			Set<Role> roles = new HashSet<>();
			roles.add(roleRepository.findByAuthority("ROLE_ADMIN"));
			User admin = new User("admin", "123", "admin@gmail", roles);
			userRepository.save(admin);
		}
		if (userRepository.findByUserId(0) == null) {
			for (int i = 0; i < 5; ++i) {
				Set<Role> roles = new HashSet<>();
				roles.add(roleRepository.findByAuthority("ROLE_USER"));
				User user = new User("ASd", "base", "asdasd", roles);
				userRepository.save(user);
			}
		}
	}

}
