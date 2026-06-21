package dev.arthdroid.billmanagerapp.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BillRequestDTO( @NotNull BigDecimal cost, @NotNull Long categoryId, @NotNull LocalDate dueDate, @NotBlank String description ) {

}
