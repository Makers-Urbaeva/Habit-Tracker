package com.example.dto.request;

import lombok.Builder;

@Builder
public record ForgotPasswordRequest(
        String email
) {
}