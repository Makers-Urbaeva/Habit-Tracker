package com.example.dto.request;

import jakarta.validation.constraints.*;

public record UserUpdateRequest (
        Long id,
        @Size(min = 2, max = 30, message = "The name must contain between 2 and 30 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "The Firstname must contain only letters.")
        @NotBlank(message = "Full name cannot be empty!")
        @NotNull(message = "Full name cannot be empty!")
        String fullName,
        String icon
){
}
