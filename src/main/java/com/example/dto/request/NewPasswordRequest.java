package com.example.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import com.example.validation.PasswordValid;

@Builder
public record NewPasswordRequest(
        @PasswordValid(message = "Invalid password!")
        @NotBlank(message = "Password cannot be empty!")
        String newPassword,
        String token
) {
}