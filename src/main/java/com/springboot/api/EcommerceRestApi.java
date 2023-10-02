package com.springboot.api;

import com.springboot.api.entity.Role;
import com.springboot.api.repository.RoleRepository;
import com.springboot.api.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class EcommerceRestApi implements CommandLineRunner {
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceRestApi.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			// Check if admin role exists
			Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
			if (adminRoleOptional.isEmpty()) {
				Role adminRole = new Role();
				adminRole.setId(AppConstants.ADMIN_ID);
				adminRole.setName("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}

			// Check if user role exists
			Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
			if (userRoleOptional.isEmpty()) {
				Role userRole = new Role();
				userRole.setId(AppConstants.USER_ID);
				userRole.setName("ROLE_USER");
				roleRepository.save(userRole);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
