package com.example.dto.request;

public record AuthenticationRequest(
        String email,
        String password
) {
}
