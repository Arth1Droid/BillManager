package dev.arthdroid.billmanagerapp.mappers;

import dev.arthdroid.billmanagerapp.dtos.UserResponseDTO;
import dev.arthdroid.billmanagerapp.models.User;

public class UserMapper {
	
	
	public static UserResponseDTO toResponse(User user) {
		return new UserResponseDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getCreatedAt()
				);
	}
}
