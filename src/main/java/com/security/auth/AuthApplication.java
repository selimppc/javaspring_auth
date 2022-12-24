package com.security.auth;

import com.security.auth.Entity.ERole;
import com.security.auth.Entity.Role;
import com.security.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
		System.out.println("Hello Auth!");
	}


	/* Add some rows into roles collection
	 * before assigning any role to Employee. */
	@Override
	public void run(String... args) throws Exception {
		try {
			if (roleRepository.findAll().isEmpty()) {
				Role role = new Role();
				role.setName(ERole.ROLE_EMPLOYEE.name());
				roleRepository.save(role);
				Role role2 = new Role();
				role2.setName(ERole.ROLE_ADMIN.name());
				roleRepository.save(role2);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
