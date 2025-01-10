package com.bookmyflight.security.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.bookmyflight.security.model.Role;
import com.bookmyflight.security.model.Users;


@Repository

public interface UserRepo extends JpaRepository<Users, Integer> {
	

	Users findByUsername(String username);
	Users findByRole(Role role);
	Boolean existsByUsername(String username);
	Users save(UserDetails user);
}
