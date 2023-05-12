package com.example.dto.response;

public record UserProfileResponse(
        Long id,
        String fullName,
        String icon,
        String email
) {
}
