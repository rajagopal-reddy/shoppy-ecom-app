package com.ecom.shoppy.repository;

import com.ecom.shoppy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUserName(String username);
	Boolean existsByEmail(String email);
	Boolean existsByUserName(String username);

}