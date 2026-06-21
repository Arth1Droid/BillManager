package dev.arthdroid.billmanagerapp.mappers;

import dev.arthdroid.billmanagerapp.dtos.BillRequestDTO;
import dev.arthdroid.billmanagerapp.dtos.BillResponseDTO;
import dev.arthdroid.billmanagerapp.models.Bill;
import dev.arthdroid.billmanagerapp.models.Category;

public class BillMapper {
    
	public static Bill toEntity(BillRequestDTO dto, Category category ) {
		Bill bill = new Bill(
				category,
	            dto.cost(),
	            dto.description(),
	            dto.dueDate()
	        );
	        return bill;
	    }

    public static BillResponseDTO toResponse(Bill bill) {
        return new BillResponseDTO(
                bill.getId(),
                bill.getCost(),
                bill.getCategory().getId(),
                bill.getCategory().getName(),
                bill.getPayDay(),
                bill.getDueDate(),
                bill.getDescription()
            );
    }
 }

