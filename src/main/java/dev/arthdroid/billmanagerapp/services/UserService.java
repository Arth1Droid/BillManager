package dev.arthdroid.billmanagerapp.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.arthdroid.billmanagerapp.dtos.RegisterUserRequestDTO;
import dev.arthdroid.billmanagerapp.dtos.RegisterUserResponseDTO;
import dev.arthdroid.billmanagerapp.dtos.UserResponseDTO;
import dev.arthdroid.billmanagerapp.dtos.UserUpdateDTO;
import dev.arthdroid.billmanagerapp.exceptions.EmailAlreadyRegisteredException;
import dev.arthdroid.billmanagerapp.exceptions.UserNotFoundException;
import dev.arthdroid.billmanagerapp.mappers.RegisterUserMapper;
import dev.arthdroid.billmanagerapp.mappers.UserMapper;
import dev.arthdroid.billmanagerapp.models.User;
import dev.arthdroid.billmanagerapp.repositories.UserRepository;
import dev.arthdroid.billmanagerapp.security.AuthenticatedUserUtil;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public RegisterUserResponseDTO registerUser(RegisterUserRequestDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		if(userRepository.existsByEmail(dto.email())) {
			throw new EmailAlreadyRegisteredException("Email already registered");
		}
		User user = RegisterUserMapper.toEntity(dto);
		user.setPassword(passwordEncoder.encode(dto.password()));
		user = userRepository.save(user);
		
		return RegisterUserMapper.toResponse(user);
	}
	
	public UserResponseDTO findById(Long id) {
		User user = findEntityById(id);			
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO findByEmail(String email) {
		User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));	
		return UserMapper.toResponse(user);		
	}
	
	public UserResponseDTO updateUser( UserUpdateDTO dto) {
		if(dto == null) {
			throw new IllegalArgumentException("The request cannot be null");
		}
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();

		
		userRepository.findUserByEmail(dto.email())
        .ifPresent(existingUser -> {
            if (!existingUser.getId().equals(authenticatedUserId)) {
                throw new EmailAlreadyRegisteredException("Email already registered");
            }
        });

		User user = findEntityById(authenticatedUserId);	
		updateData(user, dto);
		userRepository.save(user);
		return UserMapper.toResponse(user);
	}
	
	public void deleteUser() {
		Long authenticatedUserId = AuthenticatedUserUtil.getAuthenticatedUserId();
		User user = findEntityById(authenticatedUserId);	
		userRepository.delete(user);
	}
	
	public UserResponseDTO getAuthenticatedUser() {

	    Long authenticatedUserId =
	            AuthenticatedUserUtil.getAuthenticatedUserId();

	    User user = findEntityById(authenticatedUserId);

	    return UserMapper.toResponse(user);
	}


    private void updateData(User entity, UserUpdateDTO dto) {
    	if(dto.name() != null && !dto.name().isBlank()) {
    		entity.setName(dto.name());	
    	}
    	if(dto.email() != null && !dto.email().isBlank()) {
    		entity.setEmail(dto.email());
    	}   	
  
	}
    
    private User findEntityById(Long id) {
    	User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    	return user;
    }
}
