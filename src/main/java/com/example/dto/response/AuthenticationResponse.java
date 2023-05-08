package com.example.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String email,
        String token,
        String role
) {
}