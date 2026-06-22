package dev.arthdroid.billmanagerapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import dev.arthdroid.billmanagerapp.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	boolean existsByEmail(String email);
	List<User>findByName(String name);
	Optional<User> findUserByEmail(String email);
}
