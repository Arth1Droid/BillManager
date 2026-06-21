package dev.arthdroid.billmanagerapp.mappers;

import dev.arthdroid.billmanagerapp.dtos.CategoryRequestDTO;
import dev.arthdroid.billmanagerapp.dtos.CategoryResponseDTO;
import dev.arthdroid.billmanagerapp.models.Category;

public class CategoryMapper {
	
	public static Category toEntity(CategoryRequestDTO dto) {
		return new Category(
				dto.name()
				);
	
	}
	
	public static CategoryResponseDTO toResponse(Category category) {
		return new CategoryResponseDTO(
				category.getId(),
				category.getName()
				);
	}
}
