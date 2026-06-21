package dev.arthdroid.billmanagerapp.dtos;

import jakarta.validation.constraints.Email;

public record UserUpdateDTO( String name,  @Email String email) {

}
