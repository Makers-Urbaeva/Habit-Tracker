package com.example.dto.request;

import jakarta.validation.constraints.*;
import com.example.validation.PasswordValid;

public record RegisterRequest(
        @Size(min = 2, max = 30, message = "The Firstname must contain between 2 and 30 characters.")
        @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(([',. -][а-яА-ЯёЁa-zA-Z ])?[а-яА-ЯёЁa-zA-Z]*)*$", message = "The Firstname must contain only letters.")
        @NotBlank(message = "Full name cannot be empty!")
        @NotNull(message = "Full name cannot be empty!")
        String fullName,
        @NotBlank(message = "Email cannot be empty!")
        @NotNull(message = "Email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,
        @PasswordValid(message = "Invalid password!")
        String password ) {
}
