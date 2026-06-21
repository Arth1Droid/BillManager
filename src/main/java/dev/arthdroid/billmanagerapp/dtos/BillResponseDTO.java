package dev.arthdroid.billmanagerapp.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;


public record BillResponseDTO( Long id, BigDecimal cost, Long categoryId, String categoryName, LocalDate payDay, LocalDate dueDate, String description ) {

}
