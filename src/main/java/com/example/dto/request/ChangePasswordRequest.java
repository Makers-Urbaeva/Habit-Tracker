package com.example.dto.request;

import lombok.Builder;

@Builder
public record ChangePasswordRequest(
        String oldPassword,
        String newPassword
) {
}