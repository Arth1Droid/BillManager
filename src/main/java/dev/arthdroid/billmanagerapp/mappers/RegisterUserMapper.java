package dev.arthdroid.billmanagerapp.mappers;

import dev.arthdroid.billmanagerapp.dtos.RegisterUserRequestDTO;
import dev.arthdroid.billmanagerapp.dtos.RegisterUserResponseDTO;
import dev.arthdroid.billmanagerapp.models.User;

public class RegisterUserMapper {
	
	public static User toEntity(RegisterUserRequestDTO dto) {
		return new User(
				dto.name(),
				dto.email(),
				dto.password()
				);
	
	}
	
	public static RegisterUserResponseDTO toResponse(User user) {
		return new RegisterUserResponseDTO(
				user.getName(),
				user.getEmail()
				);
	}
}